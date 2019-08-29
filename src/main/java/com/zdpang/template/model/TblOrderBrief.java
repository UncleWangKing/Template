package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
 * @since 2019-06-25
 */
@TableName("tbl_order_brief")
@Data
@Accessors(chain = true)
public class TblOrderBrief extends Model<TblOrderBrief> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "transaction_id", type = IdType.UUID)
    private String transactionId;

    /**
     * 父订单ID
     */
    private String parentTransactionId;

    /**
     * 彩种ID
     */
    private String gameUniqueId;

    /**
     * 彩种名称
     */
    private String gameUniqueName;

    /**
     * 彩种期号
     */
    private String gameIssueNo;

    /**
     * 代理ID
     */
    private Integer agentId;

    /**
     * 所属业主
     */
    private Integer clientId;

    /**
     * 用户ID
     */
    private Integer userUniqueId;

    private String username;

    /**
     * 下注总注数
     */
    private Integer numberOfUnits;

    /**
     * 下注详情，json格式，只记录投注的组合格式，不记录具体每注情况。
     */
    private String betEntries;

    /**
     * 返水
     */
    private BigDecimal rebate;

    /**
     * 订单金额
     */
    private BigDecimal transactionAmount;

    /**
     * 中奖金额
     */
    private BigDecimal winAmount;

    /**
     * 输赢金额
     */
    private BigDecimal winLoseAmount;

    /**
     * 订单状态
     */
    private String transactionStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间，最后一次设置时间
     */
    private LocalDateTime updateTime;

    /**
     * 注单分类
     */
    private String tag;

    /**
     * 厅主别名
     */
    private String brand;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 重试次数
     */
    private Integer retryTime;

    /**
     * 实际产生金额，主要针对追号订单，如追停和撤单
     */
    private BigDecimal totalCharge;

    /**
     * 账变流水号
     */
    private String balanceChangeId;

    /**
     * 赔率设定，记录为Json格式
     */
    private String prizeSettings;

    /**
     * 是否追停：0 - false；1 - true
     */
    private Boolean stopAfterWin;

    /**
     * 投注倍数
     */
    private Integer orderMultiplier;

    /**
     * 用户提交注单时间
     */
    private LocalDateTime submitTime;

    /**
     * 后台接收注单时间
     */
    private LocalDateTime receivingTime;

    /**
     * 开奖号码
     */
    private String openCode;

    @Override
    protected Serializable pkVal() {
        return this.transactionId;
    }
}
