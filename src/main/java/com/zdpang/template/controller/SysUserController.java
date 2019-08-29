package com.zdpang.template.controller;


import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.common.CrudController;
import com.zdpang.template.dao.SysUserMapper;
import com.zdpang.template.model.SysUser;
import com.zdpang.template.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhangDaPang 285296372@qq.com
 * @since 2019-01-16
 */
@RestController
@RequestMapping("/user")
public class SysUserController implements CrudController<SysUser, SysUserService> {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public SysUserService getService() {
        return sysUserService;
    }

    @GetMapping(value="/selectByAnno")
    ResponseBean selectByAnno(String id) throws Exception {

        return new ResponseBean().success(sysUserMapper.selectByAnno(id));
    }

    @GetMapping(value="/sqlatk")
    ResponseBean lawtype(String id) throws Exception {

        return new ResponseBean().success(sysUserService.sqlAttackTest(id));
    }

    @GetMapping(value="/stuff")
    ResponseBean stuff() throws Exception {

        return new ResponseBean().success(sysUserService.list(null));
    }
}

