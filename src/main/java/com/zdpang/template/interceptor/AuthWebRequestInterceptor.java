package com.zdpang.template.interceptor;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@ConfigurationProperties(prefix = "com.zdpang.config.auth")
public class AuthWebRequestInterceptor implements HandlerInterceptor {
    /**
     * 放着备用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}