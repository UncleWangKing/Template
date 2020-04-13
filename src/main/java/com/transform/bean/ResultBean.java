package com.transform.bean;

import lombok.Data;

import java.util.List;

@Data
public class ResultBean {
    String LastFileName;

    List<ResultItemBean> Entries;
}
