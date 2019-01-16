package com.zdpang.template.exception;

import lombok.Getter;
import lombok.Setter;

public class BaseException extends RuntimeException {
    @Getter @Setter private Integer status;
    public BaseException(String message, Integer status){
        super(message);
        this.status = status;
    }
}
