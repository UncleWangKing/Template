package com.zdpang.template.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "MessageVo", description = "消息体")
public class MessageVo {
  @ApiModelProperty(value = "发送方用户ID")
  private Long senderUserId;
  @ApiModelProperty(value = "发送方代理ID")
  private Long senderAgentId;
  @ApiModelProperty(value = "发送方客户端ID")
  private Long senderClientId;
  @ApiModelProperty(value = "发送方品牌ID")
  private String brand;
  @ApiModelProperty(value = "接收方用户ID")
  private Long targetUserId;
  @ApiModelProperty(value = "消息类型 0-单用户 1-广播")
  private Integer messageType;
  @ApiModelProperty(value = "消息状态 0 正常 1 已删除")
  private Integer messageStatus;
  @ApiModelProperty(value = "过期时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone="GMT+8")
  private Date expireTime;
  @ApiModelProperty(value = "消息体")
  private String payload;
  @ApiModelProperty(value = "幂等效验")
  private String uniqueInfo;
  @ApiModelProperty(value = "发送时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone="GMT+8")
  private Date sendTime;
  @ApiModelProperty(value = "用户类型0 玩家 1管理员")
  private Integer targetUserType;
}
