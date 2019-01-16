package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuwang
 * @since 2018-11-15
 */
@TableName("sys_user")
@Data
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String username;

    private String password;

    private String nickname;

    private Integer gender;

    private String phonenumber;

    private String cellphonenumber;

    private String email;

    private String wechat;

    private String qq;

    private String imageurl;

    private String employeenumber;

    private Date birthday;

    private Integer enable;

    private String description;

    private String organization;

    @TableField(exist = false)
    private String organizationName;

    private String department;

    @TableField(exist = false)
    private String departmentName;

    private String job;

    @TableField(exist = false)
    private String jobName;

    private String remark;

    private String address;

    private Date createtime;

    private Date lastlogintime;

    private String manager;

    @TableField(exist = false)
    private String managerName;

    private String extra;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
 }
