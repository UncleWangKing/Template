package com.zdpang.template.controller;

import com.zdpang.springviewer.context.SpringViewerContext;
import com.zdpang.template.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/16 15:16
 */
@RestController
public class SpringViewerController {
    @Autowired
    private SpringViewerContext springViewerContext;

    @GetMapping("/springviewer")
    public ResponseBean getPatrolInfoByDate() {
        return new ResponseBean().success(springViewerContext.getControllerClassBeans());
    }
}
