package com.zdpang.template.controller;


import com.zdpang.template.common.CrudController;
import com.zdpang.template.model.SysUser;
import com.zdpang.template.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Gumh
 * @since 2019-01-16
 */
@RestController
@RequestMapping("/user")
public class SysUserController implements CrudController<SysUser, SysUserService> {
    @Autowired
    SysUserService sysUserService;
    @Override
    public SysUserService getService() {
        return sysUserService;
    }
}

