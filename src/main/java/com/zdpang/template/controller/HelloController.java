package com.zdpang.template.controller;

import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.exception.BaseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/16 15:16
 */
@RestController
public class HelloController {
    @GetMapping("/hellow")
    public ResponseBean getPatrolInfoByDate() {
        return new ResponseBean().success();
    }
}
