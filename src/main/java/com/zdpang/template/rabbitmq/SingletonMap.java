package com.zdpang.template.rabbitmq;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/30 18:02
 */
public class SingletonMap {
    public Map<String, String> map = new HashMap<>();
    public static SingletonMap self = new SingletonMap();
}
