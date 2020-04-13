package com.transform.bean;

import lombok.Data;

@Data
public class ResultItemBean {
    String FullPath;

    Object chunks;

    public boolean isPath(){
        return null == chunks;
    }
}
