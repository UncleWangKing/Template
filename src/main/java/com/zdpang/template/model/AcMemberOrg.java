package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zdpang
 * @since 2019-03-28
 */
@TableName("ac_member_org")
@Data
@Accessors(chain = true)
public class AcMemberOrg extends Model<AcMemberOrg> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "org_id", type = IdType.AUTO)
    private String orgId;

    /**
     * 分类名称
     */
    private String orgName;

    /**
     * 当前分类编号
     */
    private String orgCode;

    /**
     * 完整分类编号(从第一级到当前级别,用-分隔,例如:1001-1002)
     */
    private String orgFullCode;

    /**
     * 上级ID
     */
    private String parentId;

    /**
     * 有效状态(0.无效;1.有效)
     */
    private Boolean status;

    /**
     * 分类层级(最大5级)
     */
    private Integer level;

    /**
     * 商家ID
     */
    private Integer merchantId;

    /**
     * 是否删除(0.未删除;1.已删除)
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.orgId;
    }
}
