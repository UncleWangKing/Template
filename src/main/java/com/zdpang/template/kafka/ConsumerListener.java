package com.zdpang.template.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {

    @KafkaListener(topics = "test")
    public void onMessage(String message){
        //insertIntoDb(buffer);//这里为插入数据库代码
        System.out.println(message);
    }

}