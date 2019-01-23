package com.zdpang.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdpang.template.model.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhangDaPang 285296372@qq.com
 * @since 2019-01-16
 */
public interface SysUserService extends IService<SysUser> {
    SysUser sqlAttackTest(String id);
}
