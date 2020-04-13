package com.transform.bean;

import lombok.Data;

@Data
public class UrlBean {
    String host;

    String lastFileName;

    Integer limit;

    public String getUrl(){
        return host + "/?pretty=y&limit=" + limit + "&lastFileName=" +lastFileName;
    }
}
