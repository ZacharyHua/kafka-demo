package com.study.kafkademo.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.internals.TransactionManager;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小呆呆
 * @create 2019-07-01 23:41
 **/
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    @Value("${kafka.producer.bootstrap-servers}")
    private String bootstrapServer;
    @Value("${kafka.producer.retries}")
    private Integer retries;
    @Value("${kafka.producer.linger}")
    private Integer linger;
    @Value("${kafka.producer.batch-size}")
    private Integer batchSize;
    @Value("${kafka.producer.buffer-memory}")
    private Integer bufferMemory;


    @Bean
    public Map<String,Object> producerConfigs(){
        Map<String, Object> props = new HashMap<>(7);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.put(ProducerConfig.RETRIES_CONFIG,retries);
        props.put(ProducerConfig.LINGER_MS_CONFIG,linger);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,batchSize);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String,String> producerFactory(){
        DefaultKafkaProducerFactory<String,String> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        factory.transactionCapable();
        factory.setTransactionIdPrefix("hous-");
        return factory;
    }

//    @Bean
//    public KafkaTransactionManager  transactionManager(){
//        KafkaTransactionManager manager = new KafkaTransactionManager(producerFactory());
//        return manager;
//    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}
