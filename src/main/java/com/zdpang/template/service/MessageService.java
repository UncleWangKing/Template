package com.zdpang.template.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdpang.template.bean.MessageVo;
import com.zdpang.template.model.MessageBroadcast;
import com.zdpang.template.model.MessagePayload;
import com.zdpang.template.model.MessageQueue;
import com.zdpang.template.model.MessageUser;
import com.zdpang.template.util.Constants;
import com.zdpang.template.util.Constants.MessageStatus;
import com.zdpang.template.util.Constants.MessageType;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class MessageService implements ApplicationContextAware {
  @Autowired
  private MessageQueueService messageQueueService;
  @Autowired
  private MessagePayloadService messagePayloadService;
  @Autowired
  private MessageUserService messageUserService;
  @Autowired
  private MessageBroadcastService messageBroadcastService;

  private static ApplicationContext applicationContext;

  @Transactional
  public boolean saveTest(List<MessageVo> messageVoList){
    MessageService bean = applicationContext.getBean(MessageService.class);
    bean.saveMessage1(messageVoList);
    bean.saveMessage2(messageVoList);

    return true;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public boolean saveMessage1(List<MessageVo> messageVoList){
    if(CollectionUtils.isEmpty(messageVoList)) {
      return true;
    }
    List<MessagePayload> messagePayLoadList = MessagePayload.messageVo2MessageQueue(messageVoList);
    messagePayloadService.saveBatch(messagePayLoadList);

    return true;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public boolean saveMessage2(List<MessageVo> messageVoList){
    if(CollectionUtils.isEmpty(messageVoList)) {
      return true;
    }

    List<MessagePayload> messagePayLoadList = MessagePayload.messageVo2MessageQueue(messageVoList);
    messagePayloadService.saveBatch(messagePayLoadList);
    if(true){
      throw new RuntimeException("666");
    }

    return true;
  }

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

  public List<MessageQueue> getMessage(Long userId, String brand, Integer targetUserType, Integer pageSize, Integer pageNum){
    splitBroadCast(userId, brand);
    /**
     * 消息读取
     */
    Date date = new Date();
    QueryWrapper<MessageQueue> messageQueueQueryWrapper = new QueryWrapper<>();
    messageQueueQueryWrapper.eq("target_user_id", userId).eq("brand", brand).eq("target_user_type", targetUserType).in("message_status", MessageStatus.UNREAD.getVal(), MessageStatus.READ.getVal()).ge("expire_time", date).le("send_time", date).orderByDesc("send_time");
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

  @Transactional
  void splitBroadCast(Long userId, String brand){
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
    /**
     * 无消息无需继续继续拆分
     */
    if(null == map) {
      return;
    }
    Long maxSeq = (Long) map.get("maxSeq");
    if(null == maxSeq || maxSeq <= messageUser.getSplitSeq()){
      return;
    }

    QueryWrapper<MessageBroadcast> broadcastQueryWrapper = new QueryWrapper<>();
    broadcastQueryWrapper.gt("seq", messageUser.getSplitSeq()).lt("message_status", MessageStatus.CLIENT_DELETE.getVal());
    List<MessageBroadcast> broadCastList = messageBroadcastService.list(broadcastQueryWrapper);
    List<MessageQueue> messageQueueList = MessageQueue.messageBroadCast2MessageQueue(broadCastList, userId);
    messageQueueService.saveBatch(messageQueueList);
    messageUser.setSplitSeq(maxSeq);
    messageUserService.saveOrUpdate(messageUser);
  }

  public Boolean clientUpdateStatus(Long seq, Integer status, String brand, Long userId, Integer targetUserType){
    Assert.isTrue(Constants.MessageStatus.containts(status), "未知状态");
    splitBroadCast(userId, brand);
    MessageQueue messageQueue = new MessageQueue();
    messageQueue.setMessageStatus(status);
    QueryWrapper<MessageQueue> queueQueryWrapper = new QueryWrapper<>();
    queueQueryWrapper.eq("brand", brand).eq("target_user_id", userId).eq("seq", seq).eq("target_user_type", targetUserType);

    return messageQueueService.update(messageQueue, queueQueryWrapper);
  }

  public Boolean adminUpdateStatus(Long seq, Integer status){
    /**
     * 广播消息体
     */
    Assert.isTrue(Constants.MessageStatus.containts(status), "未知状态");
    MessageBroadcast messageBroadcast = new MessageBroadcast();
    messageBroadcast.setMessageStatus(status);
    QueryWrapper<MessageBroadcast> broadcastQueryWrapper = new QueryWrapper<>();
    broadcastQueryWrapper.eq("seq", seq);
    messageBroadcastService.update(messageBroadcast, broadcastQueryWrapper);
    /**
     * 拆分消息体
     */
    MessageQueue messageQueue = new MessageQueue();
    messageQueue.setMessageStatus(status);
    QueryWrapper<MessageQueue> queueQueryWrapper = new QueryWrapper<>();
    queueQueryWrapper.eq("seq", seq);
    messageQueueService.update(messageQueue, queueQueryWrapper);

    return true;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
