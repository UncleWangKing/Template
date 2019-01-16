package com.zdpang.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdpang.template.dao.SysUserMapper;
import com.zdpang.template.model.SysUser;
import com.zdpang.template.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Gumh
 * @since 2019-01-16
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser sqlAttackTest(String id) {

        return getOne(new QueryWrapper<SysUser>().eq("id", id));
    }
}
