package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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

@TableName("message_user")
@Data
@Accessors(chain = true)
public class MessageUser extends Model<MessageUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 消息推送进度--预留
     */
    private Long pushedSeq;

    /**
     * 消息拆分进度
     */
    private Long splitSeq;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
