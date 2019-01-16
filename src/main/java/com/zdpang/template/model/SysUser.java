package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gumh
 * @since 2019-01-16
 */
@TableName("sys_user")
@Data
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Boolean gender;

    @TableField("phoneNumber")
    private String phoneNumber;

    @TableField("cellphoneNumber")
    private String cellphoneNumber;

    private String email;

    @TableField("weChat")
    private String weChat;

    private String qq;

    @TableField("imageUrl")
    private String imageUrl;

    @TableField("employeeNumber")
    private String employeeNumber;

    @TableField("birthDay")
    private LocalDate birthDay;

    private Long manager;

    private Boolean enable;

    private String description;

    private Long organization;

    private Long department;

    private Long station;

    private Long job;

    @TableField("userGroup")
    private Long userGroup;

    private String remark;

    @TableField("backUserID")
    private String backUserID;

    @TableField("backFixedLine")
    private String backFixedLine;

    @TableField("backAddress")
    private String backAddress;

    @TableField("backDeptIDs")
    private String backDeptIDs;

    @TableField("backPsIDs")
    private String backPsIDs;

    @TableField("backRoleIDs")
    private String backRoleIDs;

    @TableField("backCreateTime")
    private LocalDateTime backCreateTime;

    @TableField("backLastLoginTime")
    private LocalDateTime backLastLoginTime;

    @TableField("backLoginCount")
    private Long backLoginCount;

    @TableField("backUserStateId")
    private Boolean backUserStateId;

    @TableField("backUserImageID")
    private Long backUserImageID;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
