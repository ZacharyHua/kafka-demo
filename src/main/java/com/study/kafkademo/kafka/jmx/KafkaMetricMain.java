package com.study.kafkademo.kafka.jmx;

import com.google.common.collect.Lists;

/**
 * @author: 小呆呆
 * @create: 2019/6/27 22:59
 */
public class KafkaMetricMain {
    public static void main(String[] args) throws Exception {
        KafkaJmxConnection jmxConn = new KafkaJmxConnection("47.111.175.146:9092");
        jmxConn.init();

        while(true) {
            String topicName = "TPC_WALLET_UNFREEZE_DEDUCT_COMPENSATE";
            // 与topic无关的metric
            Object o1 = jmxConn.getValue(
                    "kafka.server:type=ReplicaManager,name=PartitionCount",
                    Lists.newArrayList("Value"));
            System.out.println(o1);
            // 与topic有关的metric
            Object o2 = jmxConn.getValue(topicName,
                    "kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec",
                    Lists.newArrayList("Count", "OneMinuteRate", "FiveMinuteRate"));
            System.out.println(o2);
            Thread.sleep(5000);
        }
    }
}
