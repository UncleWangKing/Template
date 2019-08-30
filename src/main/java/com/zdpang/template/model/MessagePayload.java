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
}
