package com.study.kafkademo.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author 小呆呆
 * @create 2019-07-03 9:50
 **/
@Component
public class KafkaSender {
    private static final Logger log = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private final KafkaTemplate<String,String> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic,String message){

        ListenableFuture<SendResult<String,String>> sender = kafkaTemplate.send(new ProducerRecord<String, String>(topic,message));
        sender.addCallback(new ListenableFutureCallback<SendResult<String,String>>(){
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("数据发送成功");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("数据发送失败");
            }
        });
    }
}
