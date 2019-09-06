package com.zdpang.template.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdpang.template.bean.MessageVo;
import com.zdpang.template.model.MessageBroadcast;
import com.zdpang.template.model.MessagePayload;
import com.zdpang.template.model.MessageQueue;
import com.zdpang.template.model.MessageUser;
import com.zdpang.template.util.Constants.MessageType;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class MessageService {
  @Autowired
  private MessageQueueService messageQueueService;
  @Autowired
  private MessagePayloadService messagePayloadService;
  @Autowired
  private MessageUserService messageUserService;
  @Autowired
  private MessageBroadcastService messageBroadcastService;

  @Transactional
  public boolean saveMessage(List<MessageVo> messageVoList){
    if(CollectionUtils.isEmpty(messageVoList)) {
      return true;
    }
    List<MessagePayload> messagePayLoadList = MessagePayload.messageVo2MessageQueue(messageVoList);
    messagePayloadService.saveBatch(messagePayLoadList);
    List<MessageQueue> messageQueueList = MessageQueue.messageVo2MessageQueue(messageVoList);
    /**
     * 依赖于list stream的map操作顺序不变性匹配id
     */
    for (int i = 0; i < messageQueueList.size(); i++) {
      MessageQueue messageQueue = messageQueueList.get(i);
      MessagePayload messagePayload = messagePayLoadList.get(i);
      messageQueue.setSeq(messagePayload.getId());
    }
    /**
     * message分类处理
     */
    List<MessageBroadcast> messageBroadcastList = new ArrayList<>();
    Iterator<MessageQueue> iterator = messageQueueList.iterator();
    while (iterator.hasNext()){
      MessageQueue item = iterator.next();
      if(item.getMessageType().equals(MessageType.BROAD_CAST.getVal())){
        messageBroadcastList.add(MessageBroadcast.messageQueue2MessageBroadCast(item));
        iterator.remove();
      }
    }

    if(! CollectionUtils.isEmpty(messageQueueList)) {
      messageQueueService.saveBatch(messageQueueList);
    }

    if(! CollectionUtils.isEmpty(messageBroadcastList)) {
      messageBroadcastService.saveBatch(messageBroadcastList);
    }

    return true;
  }

  public List<MessageQueue> getMessage(Long userId, String brand, Long clientId, Integer pageSize, Integer pageNum){
    /**
     * 读取用户广播拆分情况
     */
    QueryWrapper<MessageUser> messageUserQueryWrapper = new QueryWrapper<>();
    messageUserQueryWrapper.eq("user_id", userId);
    MessageUser messageUser = messageUserService.getOne(messageUserQueryWrapper);
    if(null == messageUser){
      messageUser = MessageUser.generateMessageUser(userId);
    }
    /**
     * 广播拆分
     */
    QueryWrapper<MessageBroadcast> maxSeqWrapper = new QueryWrapper<>();
    maxSeqWrapper.select("MAX(seq) as maxSeq").eq("brand", brand);
    Map<String, Object> map = messageBroadcastService.getMap(maxSeqWrapper);
    Long maxSeq = (Long) map.get("maxSeq");
    if(null != maxSeq && maxSeq > messageUser.getSplitSeq()){
      QueryWrapper<MessageBroadcast> broadcastQueryWrapper = new QueryWrapper<>();
      broadcastQueryWrapper.gt("seq", messageUser.getSplitSeq());
      List<MessageBroadcast> broadCastList = messageBroadcastService.list(broadcastQueryWrapper);
      List<MessageQueue> messageQueueList = MessageQueue.messageBroadCast2MessageQueue(broadCastList, userId);
      messageQueueService.saveBatch(messageQueueList);
      messageUser.setSplitSeq(maxSeq);
      messageUserService.saveOrUpdate(messageUser);
    }

    /**
     * 消息读取
     */
    QueryWrapper<MessageQueue> messageQueueQueryWrapper = new QueryWrapper<>();
    messageQueueQueryWrapper.eq("target_user_id", userId).eq("brand", brand).ge("expire_time", new Date()).orderByDesc("seq");
    List<MessageQueue> messageQueueList = messageQueueService.list(messageQueueQueryWrapper);

    if(! CollectionUtils.isEmpty(messageQueueList)) {
      List<Long> payLoadIdList = new ArrayList<>();
      for (MessageQueue messageQueue:messageQueueList) {
        payLoadIdList.add(messageQueue.getSeq());
      }
      QueryWrapper<MessagePayload> payloadQueryWrapper = new QueryWrapper<>();
      payloadQueryWrapper.in("id", payLoadIdList);
      List<MessagePayload> payloadList = messagePayloadService.list(payloadQueryWrapper);

      for (MessageQueue messageQueue:messageQueueList) {
        for (MessagePayload payload:payloadList) {
          if(payload.getId().equals(messageQueue.getSeq())){
            messageQueue.setPayLoad(payload.getPayload());
            break;
          }
        }
      }

    }
    return messageQueueList;
  }
}