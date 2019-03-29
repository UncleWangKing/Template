package com.zdpang.template.controller;


import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.model.AcMemberOrg;
import com.zdpang.template.service.AcMemberOrgService;
import com.zdpang.template.util.TreeNodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdpang
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/ac")
public class AcMemberOrgController {
    @Autowired
    private AcMemberOrgService acMemberOrgService;

    @GetMapping(value="/stuff")
    ResponseBean stuff() throws Exception {
        List<AcMemberOrg> list = acMemberOrgService.list(null);
        for (AcMemberOrg ac:list) {
            if(ac.getParentId().equals("0")) {
                ac.setParentId("");
            }
        }
        return new ResponseBean().success(new TreeNodeUtil<AcMemberOrg, String>().generateTree(list, "orgId", "parentId"));
    }
}

