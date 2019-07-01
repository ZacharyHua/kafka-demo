package com.study.kafkademo.kafka;

import org.apache.kafka.clients.consumer.*;

import java.util.Arrays;
import java.util.Properties;

/**
 * 测试消费者类
 *
 * @author: 小呆呆
 * @create: 2019/6/27 21:57
 */
public class KafkaConsumerNew {
    private Consumer<String,String> consumer;
    private static String group = "group-1";
    private static String TOPIC = "test2";

    public KafkaConsumerNew(){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.111.175.146:2182");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true"); // 自动commit
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000"); // 自动commit的间隔
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<String, String>(props);
    }

    private void consume() throws InterruptedException {
        consumer.subscribe(Arrays.asList(TOPIC)); // 可消费多个topic,组成一个list
        while (true){
            ConsumerRecords<String,String> record = consumer.poll(1000);
            for (ConsumerRecord<String,String> consumerRecord : record){
                System.out.printf("offset = %d, key = %s, value = %s \n", consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());
                Thread.sleep(1000L);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new KafkaConsumerNew().consume();
    }

}
