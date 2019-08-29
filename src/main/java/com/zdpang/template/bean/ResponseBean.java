package com.zdpang.template.bean;

import lombok.Getter;

public class ResponseBean {

    private static final String OK = "ok";
    private static final String ERROR = "error";
    private static final Integer SUCCESS_STATUS = 200;
    private static final Integer FAILURE_STATUS = 500;

    @Getter private Meta meta;
    @Getter private Object data;

    public ResponseBean success() {
        this.meta = new Meta(true, OK);
        return this;
    }

    public ResponseBean success(Object data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }

    public ResponseBean failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public ResponseBean failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }

    public ResponseBean failure(String message, Integer status) {
        this.meta = new Meta(false, message, status);
        return this;
    }

    public ResponseBean(){

    }

    public ResponseBean(boolean isSuccess){
        this.meta = new Meta(isSuccess);
    }

    public ResponseBean(boolean isSuccess, String message){
        this.meta = new Meta(isSuccess, message);
    }

    public class Meta {

        @Getter private boolean success;
        @Getter private String message;
        @Getter private Integer status;

        public Meta(boolean success) {
            this.success = success;
            this.status = success ? SUCCESS_STATUS : FAILURE_STATUS;
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
            this.status = success ? SUCCESS_STATUS : FAILURE_STATUS;
        }

        public Meta(boolean success, String message, Integer status) {
            this.success = success;
            this.message = message;
            this.status = status;
        }
    }
}