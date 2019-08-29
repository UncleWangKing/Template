package com.zdpang.template.redis;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/23 16:20
 */
public class RedisCluster {
    private static JedisCluster jedis;

    static {
        // 第一步：使用JedisCluster对象。需要一个Set<HostAndPort>参数。Redis节点的列表。
        Set<HostAndPort> hostAndPortsSet = new HashSet<>();
        hostAndPortsSet.add(new HostAndPort("39.105.109.109",7000));
        hostAndPortsSet.add(new HostAndPort("39.105.109.109",7001));
        hostAndPortsSet.add(new HostAndPort("39.105.109.109",7002));
        hostAndPortsSet.add(new HostAndPort("39.105.109.109",7003));
        hostAndPortsSet.add(new HostAndPort("39.105.109.109",7004));
        hostAndPortsSet.add(new HostAndPort("39.105.109.109",7005));

        jedis = new JedisCluster(hostAndPortsSet);
    }

    public static JedisCluster getJedis(){
        return jedis;
    }

    public static void close() throws IOException {
        jedis.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        // 第二步：直接使用JedisCluster对象操作redis。在系统中单例存在。
        jedis.set("hello", "120");
        String result = jedis.get("hello");
        // 第三步：打印结果
        System.out.println(result);
        // 第四步：系统关闭前，关闭JedisCluster对象。
        close();
    }
}
