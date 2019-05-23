package com.zdpang.template.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdpang.template.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhangDaPang 285296372@qq.com
 * @since 2019-01-16
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户Id返回用户数据
     * @param userId 用户Id
     * @return 用户数据
     */
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })
    @Select({ "SELECT "
            + " id , "
            + " username , "
            + " password "
            + " FROM "
            + " sys_user "
            + " WHERE "
            + " id = #{userId}" })
    SysUser selectByAnno(String userId);
}
