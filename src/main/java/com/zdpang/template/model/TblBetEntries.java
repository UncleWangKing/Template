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
 * 订单玩法表
 * </p>
 *
 * @author zdpang
 * @since 2019-06-25
 */
@TableName("tbl_bet_entries")
@Data
@Accessors(chain = true)
public class TblBetEntries extends Model<TblBetEntries> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id+序号的方式。
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 订单ID
     */
    private String transactionId;

    /**
     * 彩种编码，没有记录彩种名称。
     */
    private String gameUniqueId;

    /**
     * 彩种期号
     */
    private String gameIssueNo;

    /**
     * 玩法编码
     */
    private String gameplayMethod;

    /**
     * 玩法名称
     */
    private String gameplayMethodName;

    /**
     * 用户ID
     */
    private Long userUniqueId;

    /**
     * 投注号码，直接记录投注字符串
     */
    private String number;

    /**
     * 单注金额
     */
    private BigDecimal pricePerUnit;

    /**
     * 注数
     */
    private Integer numberOfUnits;

    /**
     * 投注金额
     */
    private BigDecimal amount;

    /**
     * 返水
     */
    private BigDecimal returnMoneyRatio;

    /**
     * 返水金额
     */
    private BigDecimal returnMoneyAmount;

    /**
     * 输赢结果：0未开奖；1-赢；2-输；3-和；4-异常。
     */
    private Integer outcome;

    /**
     * 中奖金额
     */
    private BigDecimal winningAmount;

    /**
     * 附加信息
     */
    private String additionalInfo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间，最后一次设置时间
     */
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
