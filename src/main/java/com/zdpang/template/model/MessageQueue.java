package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
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
    /**
     * 消息体
     */
    private Long payloadId;

    private Long seq;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
