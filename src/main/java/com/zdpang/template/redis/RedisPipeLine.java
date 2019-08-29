package com.zdpang.template.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/1/28 15:55
 */
@Slf4j
public class RedisPipeLine {
    public static void main(String[] args) throws InterruptedException {
        Integer count = 100;
        Jedis jedis = Redis.getJedis();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            jedis.set(String.valueOf(i), String.valueOf(i));
        }
        long end = System.currentTimeMillis();
        log.info(100 + "次总时间:" + (end - start));

        Pipeline pipe = jedis.pipelined(); // 先创建一个pipeline的链接对象
        long start_pipe = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            pipe.set(String.valueOf(i), String.valueOf(i));
        }
        pipe.sync(); // 获取所有的response
        long end_pipe = System.currentTimeMillis();
        log.info("pipe line 总时间:" + (end_pipe - start_pipe));

        BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            logQueue.put("i=" + i);
        }
        long stop = System.currentTimeMillis();
        log.info("BlockingQueue 总时间:" + (stop - begin));
    }
}
