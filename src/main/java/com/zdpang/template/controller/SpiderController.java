package com.zdpang.template.controller;

import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.util.SpiderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/16 15:16
 */
@RestController
public class SpiderController {
    @Autowired
    SpiderHandler spiderHandler;
    @GetMapping("/spider")
    public ResponseBean getPatrolInfoByDate() {
        spiderHandler.spiderData();
        return new ResponseBean().success();
    }
}
