package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.regexp.internal.RE;
import com.zdpang.template.bean.MessageVo;
import java.time.LocalDateTime;
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
 * @since 2019-08-30
 */
@TableName("message_queue")
@Data
@Accessors(chain = true)
public class MessageQueue extends Model<MessageQueue> {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone="GMT+8")
    private Date createTime;
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone="GMT+8")
    private Date expireTime;

    private Long seq;

    @TableField(exist = false)
    private String payLoad;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static MessageQueue messageVo2MessageQueue(MessageVo messageVo){
        MessageQueue messageQueue = new MessageQueue();
        messageQueue.setSenderUserId(messageVo.getSenderUserId());
        messageQueue.setSenderAgentId(messageVo.getSenderAgentId());
        messageQueue.setSenderClientId(messageVo.getSenderClientId());
        messageQueue.setBrand(messageVo.getBrand());
        messageQueue.setTargetUserId(messageVo.getTargetUserId());
        messageQueue.setMessageType(messageVo.getMessageType());
        messageQueue.setMessageStatus(messageVo.getMessageStatus());
        messageQueue.setExpireTime(messageVo.getExpireTime());

        return messageQueue;
    }

    public static List<MessageQueue> messageVo2MessageQueue(List<MessageVo> messageVoList){
        return messageVoList.stream().map(MessageQueue::messageVo2MessageQueue).collect(
            Collectors.toList());
    }

    public static MessageQueue messageBroadCast2MessageQueue(MessageBroadcast messageBroadcast, Long targetUserId){
        MessageQueue messageQueue = new MessageQueue();
        messageQueue.setSenderUserId(messageBroadcast.getSenderUserId());
        messageQueue.setSenderAgentId(messageBroadcast.getSenderAgentId());
        messageQueue.setSenderClientId(messageBroadcast.getSenderClientId());
        messageQueue.setBrand(messageBroadcast.getBrand());
        messageQueue.setTargetUserId(targetUserId);
        messageQueue.setMessageType(messageBroadcast.getMessageType());
        messageQueue.setMessageStatus(messageBroadcast.getMessageStatus());
        messageQueue.setExpireTime(messageBroadcast.getExpireTime());
        messageQueue.setSeq(messageBroadcast.getSeq());

        return messageQueue;
    }

    public static List<MessageQueue> messageBroadCast2MessageQueue(List<MessageBroadcast> messageBroadcastList, Long targetUserId){
        return messageBroadcastList.stream().map(m -> {return messageBroadCast2MessageQueue(m, targetUserId);}).collect(
            Collectors.toList());
    }
}
