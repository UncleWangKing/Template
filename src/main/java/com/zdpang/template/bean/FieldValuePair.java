package com.zdpang.template.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2018/9/19 16:30
 */
@Data
@Accessors(chain = true)
public class FieldValuePair {
    private String field;
    private Object value;
}
