package com.study.kafkademo.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.UUID;

/**
 * @author 小呆呆
 * @create 2019-07-01 23:46
 **/
@Component
public class Producer {
    private static final Logger log =LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    public void sendMessage(Message message){

        log.info("kafka send message start");
        //内部组织消息
        message.setId("key"+System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        try{
            kafkaTemplate.send(kafkaTemplate.getDefaultTopic(),gson.toJson(message));
        }catch (Exception e){
            log.error("发送数据出错！！！{}{}", kafkaTemplate.getDefaultTopic(), gson.toJson(message));
            log.error("发送数据出错=====>", e);
        }

        // 消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                // 发送成功的处理逻辑
            }

            @Override
            public void onError(ProducerRecord<String, String> producerRecord, Exception exception) {
                // 发送失败时的处理逻辑
            }

            @Override
            public boolean isInterestedInSuccess() {
                log.info("数据发送完毕");
                return false;
            }
        });
        log.info("kafka sendMessage end");
    }

    public void sendMessage(String topic, String data) {
        log.info("kafka sendMessage start");
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", throwable, topic, data);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("kafka sendMessage success topic = {}, data = {}",topic, data);
            }
        });
        log.info("kafka sendMessage end");
    }

}