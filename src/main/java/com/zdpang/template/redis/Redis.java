package com.zdpang.template.redis;


import com.zdpang.template.redis.lock.RedisTool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.Set;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/23 16:20
 */
public class Redis {
    private static Jedis jedis;
    static {
        jedis = new Jedis("39.105.109.109", 7000);
    }
    public static void main(String[] args) throws InterruptedException {
        transactionTest();
    }

    public static Jedis getJedis(){
        return jedis;
    }

    public static void close(){
        jedis.close();
    }

    public static void mapTest(){
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "lw");
        map.put("password", "123");
        jedis.hmset("lwMap", map);
        System.out.println(jedis.hmget("lwMap", "username", "password"));
        close();
    }

    public static void lockTest(){
        String keyName = "lock1";
        String requestId = "self";
        System.out.println(RedisTool.tryGetDistributedLock(jedis, keyName, requestId, 10000));
        System.out.println(RedisTool.releaseDistributedLock(jedis, keyName, requestId));
        close();
    }

    public static void keysTest(){
        Set<String> keys = jedis.keys("*");
        for (String key:keys) {
            System.out.println(key);
        }

        close();
    }

    public static void transactionTest() throws InterruptedException {

        jedis.watch("lw");
        Transaction multi = jedis.multi();
//        multi.watch("lw");
        Thread.sleep(10000l);
//        multi.discard();
        multi.set("lw", "444");
        multi.exec();
        jedis.unwatch();
        Thread.sleep(10000l);
        close();
    }
}
