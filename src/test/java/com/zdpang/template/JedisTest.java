package com.zdpang.template;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/23 16:20
 */
public class JedisTest {
    public static void main(String[] args) {
//        Jedis jedis = new Jedis("39.105.109.109", 6379);  //指定Redis服务Host和port
//        jedis.auth("root");
//        String value = jedis.set("lw", "22"); //访问Redis服务
//        System.out.println(jedis.get("lw"));
//        jedis.close(); //使用完关闭连接

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        JedisPool pool = new JedisPool(config, "39.105.109.109", 7000, 2000);
        Jedis jedis = pool.getResource();
        System.out.println(jedis.get("lw"));
        jedis.close();
        pool.close();
    }
}
