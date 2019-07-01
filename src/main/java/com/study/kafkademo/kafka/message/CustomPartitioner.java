package com.study.kafkademo.kafka.message;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 分区策略
 *
 * @author 小呆呆
 * @create 2019-07-01 16:51
 **/
public class CustomPartitioner implements Partitioner {

    // 控制分区
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        return 1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
