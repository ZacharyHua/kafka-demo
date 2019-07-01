package com.study.kafkademo.consumer;

import org.springframework.kafka.annotation.KafkaListener;

/**
 * 测试的消费者
 *
 * @author 小呆呆
 * @create 2019-07-01 23:42
 **/
public class Consumer {

    @KafkaListener(topics = {"test","test2"})
    public void processMessage(String context){
        System.out.println("消息被消费"+context);
    }
}
