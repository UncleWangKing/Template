package com.zdpang.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdpang.template.model.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gumh
 * @since 2019-01-16
 */
public interface SysUserService extends IService<SysUser> {
    SysUser sqlAttackTest(String id);
}
