package com.study.kafkademo;

import com.study.kafkademo.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaDemoApplicationTests {
    @Autowired
    private Producer producer;

    public void contextLoads() {
        producer.sendMessage("test", "topic--------test");
        producer.sendMessage("test2", "topic--------test2");
    }
}
