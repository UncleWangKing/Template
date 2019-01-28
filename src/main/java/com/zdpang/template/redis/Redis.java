package com.zdpang.template.redis;


import com.zdpang.template.redis.lock.RedisTool;
import redis.clients.jedis.Jedis;

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
        String keyName = "lock1";
        String requestId = "self";
        System.out.println(RedisTool.tryGetDistributedLock(jedis, keyName, requestId, 10000));
        System.out.println(RedisTool.releaseDistributedLock(jedis, keyName, requestId));
        close();
    }

    public static Jedis getJedis(){
        return jedis;
    }

    public static void close(){
        jedis.close();
    }
}
