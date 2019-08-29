package com.zdpang.template.config;


import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.exception.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    private ResponseBean exceptionHandler(Exception e){
        return process(e, null, null);
    }
    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    private ResponseBean noHandlerFoundExceptionHandler(Exception e){
        return process(e, 404, null);
    }
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    private ResponseBean baseExcetionHandler(BaseException e){
        return process(e, e.getStatus(), null);
    }
    public static ResponseBean process(Exception e, Integer status, String message) {
        message = null == message ? e.getMessage() : message;
        if(null == status) {
            return new ResponseBean().failure(message);
        } else {
            return new ResponseBean().failure(message, status);
        }
    }
}
