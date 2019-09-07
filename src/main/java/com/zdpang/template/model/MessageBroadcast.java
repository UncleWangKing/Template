package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zdpang.template.bean.MessageVo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zdpang
 * @since 2019-09-06
 */
@TableName("message_broadcast")
@Data
@Accessors(chain = true)
public class MessageBroadcast extends Model<MessageBroadcast> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发送者ID
     */
    private Long senderUserId;

    private Long senderAgentId;

    private Long senderClientId;

    private String brand;

    /**
     * 接收方ID
     */
    private Long targetUserId;

    /**
     * 0-单用户 1-广播
     */
    private Integer messageType;

    /**
     * 0 正常 1 已删除
     */
    private Integer messageStatus;

    /**
     * 创建时间
     */
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone="GMT+8")
    private Date expireTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone="GMT+8")
    private Date sendTime;

    private Long seq;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static MessageBroadcast messageVo2MessageBroadCast(MessageVo messageVo){
        MessageBroadcast messageQueue = new MessageBroadcast();
        messageQueue.setSenderUserId(messageVo.getSenderUserId());
        messageQueue.setSenderAgentId(messageVo.getSenderAgentId());
        messageQueue.setSenderClientId(messageVo.getSenderClientId());
        messageQueue.setBrand(messageVo.getBrand());
        messageQueue.setTargetUserId(messageVo.getTargetUserId());
        messageQueue.setMessageType(messageVo.getMessageType());
        messageQueue.setMessageStatus(messageVo.getMessageStatus());
        messageQueue.setExpireTime(messageVo.getExpireTime());
        messageQueue.setSendTime(messageVo.getSendTime());

        return messageQueue;
    }

    public static List<MessageBroadcast> messageVo2MessageBroadCast(List<MessageVo> messageVoList){
        return messageVoList.stream().map(MessageBroadcast::messageVo2MessageBroadCast).collect(
            Collectors.toList());
    }

    public static MessageBroadcast messageQueue2MessageBroadCast(MessageQueue messageQueue){
        MessageBroadcast messageBroadcast = new MessageBroadcast();
        messageBroadcast.setSenderUserId(messageQueue.getSenderUserId());
        messageBroadcast.setSenderAgentId(messageQueue.getSenderAgentId());
        messageBroadcast.setSenderClientId(messageQueue.getSenderClientId());
        messageBroadcast.setBrand(messageQueue.getBrand());
        messageBroadcast.setTargetUserId(messageQueue.getTargetUserId());
        messageBroadcast.setMessageType(messageQueue.getMessageType());
        messageBroadcast.setMessageStatus(messageQueue.getMessageStatus());
        messageBroadcast.setExpireTime(messageQueue.getExpireTime());
        messageBroadcast.setSeq(messageQueue.getSeq());
        messageBroadcast.setSendTime(messageQueue.getSendTime());

        return messageBroadcast;
    }

    public static List<MessageBroadcast> messageQueue2MessageBroadCast(List<MessageQueue> messageQueueList){
        return messageQueueList.stream().map(MessageBroadcast::messageQueue2MessageBroadCast).collect(
            Collectors.toList());
    }
}
