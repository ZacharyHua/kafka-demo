package com.study.kafkademo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * 测试消费者
 *
 * @author 小呆呆
 * @create 2019-06-27 17:36
 **/
public class ConsumerNew {
    private final KafkaConsumer<Integer,String> consumer;
    private final String topic;

    public ConsumerNew(String topic) {
        Properties properties = new Properties();
        properties.put("zookeeper.connect","47.111.175.146:2182");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"group-test");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // latest,earliest,none latest:读取最新的，earliest:从头开始
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(properties);
        this.topic = topic;
    }

    public void consumerMsg(){
        try {
            consumer.subscribe(Collections.singletonList(this.topic));
            //System.out.println(consumer.listTopics());
            while(true){
                ConsumerRecords<Integer, String> records = consumer.poll(2000);
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at partition "+record.partition()+" offset " + record.offset());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ConsumerNew Consumer = new ConsumerNew("test1");
        Consumer.consumerMsg();
    }
}
