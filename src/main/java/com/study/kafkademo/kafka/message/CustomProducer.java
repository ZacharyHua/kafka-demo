package com.study.kafkademo.kafka.message;

        import org.apache.kafka.clients.consumer.ConsumerConfig;
        import org.apache.kafka.clients.producer.*;

        import java.util.Properties;

/**
 * @author 小呆呆
 * @create 2019-07-01 17:03
 **/
public class CustomProducer {

    public static void main(String[] args) {
        Properties prots = new Properties();
        prots.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"");
        prots.put(ProducerConfig.ACKS_CONFIG,"all");
        prots.put(ProducerConfig.RETRIES_CONFIG,0);
        prots.put(ProducerConfig.BATCH_SIZE_CONFIG,0);
        prots.put(ProducerConfig.LINGER_MS_CONFIG,1);
        prots.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);
        // key序列化
        prots.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        prots.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(prots);

        for (int i = 0; i < 50; i++){
            producer.send(new ProducerRecord<>("test", "hh" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {

                }
            });
        }
        producer.close();
    }


}
