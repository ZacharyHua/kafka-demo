package com.study.kafkademo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author: 小呆呆
 * @create: 2019/6/27 22:20
 */
public class KafkaProducerNew {
    private final KafkaProducer<String,String> producer;
    public final static String TOPIC = "test";

    private KafkaProducerNew(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.111.175.146:9092");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        producer = new KafkaProducer<String, String>(props);
    }

    private void produce(){
        int messageNo = 1;
        final int COUNT = 10;

        while(messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = String.format("hello KafkaProducer message %s", key);

            try {
                producer.send(new ProducerRecord<String, String>(TOPIC, data));
            } catch (Exception e) {
                e.printStackTrace();
            }

            messageNo++;
        }

        producer.close();
    }
    public static void main(String[] args) {
        new KafkaProducerNew().produce();
    }

}
