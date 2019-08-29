package com.gson;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserBean {
    //变量名跟JSON数据的字段名需要一致
    private String name ;
    private String age;
    private String phone;
    private String email;
}