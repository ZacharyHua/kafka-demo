package com.study.kafkademo.kafka.message;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * 回调测试
 *
 * @author 小呆呆
 * @create 2019-07-01 16:16
 **/
public class CallBackProducer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        // Kafka服务端的主机名和端口号
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.111.175.146:9092");
        // 等待所有副本节点的应答
        props.put(ProducerConfig.ACKS_CONFIG,"all");
        // 消息发送最大尝试次数
        props.put(ProducerConfig.RETRIES_CONFIG,0);
        // 一批消息处理大小
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);
        // 增加服务端请求延时
        props.put(ProducerConfig.LINGER_MS_CONFIG,1);
        // 发送缓存区内存大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        // 自定义分区
//		props.put("partitioner.class", "com.atguigu.kafka.CustomPartitioner");

        KafkaProducer<String,String> kafkaProducer = new KafkaProducer<String, String>(props);

        for (int i = 1; i < 50; i++){
            Thread.sleep(500L);
            kafkaProducer.send(new ProducerRecord<String, String>("test1", "hh" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (recordMetadata != null){
                        System.out.println(recordMetadata.partition() + "--" + recordMetadata.offset());
                    }
                }
            });
        }
        kafkaProducer.close();
    }
}
