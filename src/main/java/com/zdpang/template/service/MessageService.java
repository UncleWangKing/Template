package com.zdpang.template.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdpang.template.bean.MessageVo;
import com.zdpang.template.model.MessageBroadcast;
import com.zdpang.template.model.MessagePayload;
import com.zdpang.template.model.MessageQueue;
import com.zdpang.template.model.MessageUser;
import com.zdpang.template.util.Constants;
import com.zdpang.template.util.Constants.MessageType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
      if(item.getMessageType().equals(MessageType.BROAD_CAST)){
        messageBroadcastList.add(MessageBroadcast.messageQueu2MessageBroadCast(item));
        iterator.remove();
      }
    }

    if(! CollectionUtils.isEmpty(messageQueueList)) {
      messageQueueService.saveBatch(messageQueueList);
    }

    if(! CollectionUtils.isEmpty(messageBroadcastList)) {
      messageBroadcastService.saveBatch(messageBroadcastList);
    }

    /**
     * 用户增量添加 -- 放在读取上去
     */

    if(! CollectionUtils.isEmpty(messageQueueList)) {

      Set<Long> userIdSet = new HashSet<>();
      for (MessageQueue messageQueue:messageQueueList) {
        userIdSet.add(messageQueue.getTargetUserId());
      }
      QueryWrapper<MessageUser> messageUserQueryWrapper = new QueryWrapper<>();
      messageUserQueryWrapper.select("user_id").in("user_id", userIdSet);
      List<MessageUser> messageOldUserList = messageUserService.list(messageUserQueryWrapper);

      Iterator<Long> userIdIterator = userIdSet.iterator();
      while (userIdIterator.hasNext()){
        Long next = userIdIterator.next();

      }
    }

    return true;
  }
}
