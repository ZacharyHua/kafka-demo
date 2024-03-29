package com.study.kafkademo.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小呆呆
 * @create 2019-07-03 10:04
 **/
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.consumer.enable-auto-commit}")
    private Boolean autoCommit;
    @Value("${kafka.consumer.auto-commit-interval}")
    private Integer autoCommitInterval;
    @Value("${kafka.consumer.max-poll-records}")
    private Integer maxPollRecords;
    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("#{'${kafka.listener.concurrencys}'.split(',')[0]}")
    private Integer concurrency3;
    @Value("#{'${kafka.listener.concurrencys}'.split(',')[1]}")
    private Integer concurrency6;
    @Value("${kafka.consumer.poll-timeout}")
    private Integer pollTimeout;
    @Value("${kafka.consumer.session-timeout}")
    private String  sessionTimeout;
    @Value("${kafka.listener.batch-listener}")
    private Boolean batchListener;
    @Value("${kafka.consumer.max-poll-interval}")
    private Integer maxPollInterval;
    @Value("${kafka.consumer.max-partition-fetch-bytes}")
    private Integer maxPartitionFetchBytes;

    @Bean
    @ConditionalOnMissingBean(name = "kafkaBatchListener6")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener6(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = kafkaListenerContainerFactory();
        factory.setConcurrency(concurrency6);
        return factory;
    }
    @Bean
    @ConditionalOnMissingBean(name = "kafkaBatchListener3")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener3(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = kafkaListenerContainerFactory();
        factory.setConcurrency(concurrency6);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 批量消费
        factory.setBatchListener(batchListener);
        // 如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法
        // 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
        // 手动提交无需配置
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        // 设置提交偏移量的方式 ： MANUAL_IMMEDIATE 表示消费一条提交一次；MANUAL表示批量提交一次
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }


    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String,Object> consumerConfigs(){
        Map<String,Object> props = new HashMap<>(10);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommitInterval);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
}
