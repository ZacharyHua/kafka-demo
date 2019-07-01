package com.study.kafkademo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * 普通生产者
 *
 * @author 小呆呆
 * @create 2019-06-27 17:44
 **/
public class ProducerNew {
    private final KafkaProducer<String,String> producer;
    private final String topic;

    public ProducerNew(String topic, String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.111.175.146:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG,"DemoProducer");
        props.put(ProducerConfig.ACKS_CONFIG,"all");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,16384); // 16M
        props.put(ProducerConfig.LINGER_MS_CONFIG,10); // 10mm 准备发送数据时间
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432); // 32M

        // 使用自定义分区器，如果自定义则适用默认的 DefaultPartitioner
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.study.kafka.partition.MySamplePartitioner");

        // key和value的序列化
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(props);

        this.topic = topic;

    }

    // 发送消息策略
    public void producerMsg(){

        String data = "Apache Storm is a free and open source distributed realtime computation system Storm makes it easy to reliably process unbounded streams of data doing for realtime processing what Hadoop did for batch processing. Storm is simple, can be used with any programming language, and is a lot of fun to use!\n" +
                "Storm has many use cases: realtime analytics, online machine learning, continuous computation, distributed RPC, ETL, and more. Storm is fast: a benchmark clocked it at over a million tuples processed per second per node. It is scalable, fault-tolerant, guarantees your data will be processed, and is easy to set up and operate.\n" +
                "Storm integrates with the queueing and database technologies you already use. A Storm topology consumes streams of data and processes those streams in arbitrarily complex ways, repartitioning the streams between each stage of the computation however needed. Read more in the tutorial.";
        data = data.replaceAll("[\\pP‘’“”]", "");

        String[] words = data.split(" ");
        Random random = new Random();

        Random ran = new Random();

        int event = 10;
        for (long nEvents=0; nEvents<event; nEvents++){
            long runtime = System.currentTimeMillis();
            int lastIPum = ran.nextInt(255);
            String ip = "192.168.100."+lastIPum;
            String msg = words[random.nextInt(words.length)];
            try {
                producer.send(new ProducerRecord<>(topic,ip,msg));
                System.out.println("Sent message: (" + ip + ", " + msg + ")");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerNew producerNew = new ProducerNew("test",args);
        producerNew.producerMsg();
        // 准备数据和发送数据需要时间 。。。
        Thread.sleep(200L);
    }

}
