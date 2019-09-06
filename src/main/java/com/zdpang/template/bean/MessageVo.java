package com.zdpang.template.bean;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageVo {
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
   * 过期时间
   */
  private Date expireTime;
  /**
   * 消息体
   */
  private String payload;
}
