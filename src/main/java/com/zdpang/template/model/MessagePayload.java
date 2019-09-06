package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zdpang.template.bean.MessageVo;
import java.io.Serializable;
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

@TableName("message_payload")
@Data
@Accessors(chain = true)
public class MessagePayload extends Model<MessagePayload> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String payload;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static MessagePayload messageVo2MessagePayload(MessageVo messageVo){
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setPayload(messageVo.getPayload());

        return messagePayload;
    }

    public static List<MessagePayload> messageVo2MessageQueue(List<MessageVo> messageVoList){
        return messageVoList.stream().map(MessagePayload::messageVo2MessagePayload).collect(
            Collectors.toList());
    }
}
