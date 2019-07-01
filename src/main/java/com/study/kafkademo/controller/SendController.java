package com.study.kafkademo.controller;

import com.study.kafkademo.producer.Message;
import com.study.kafkademo.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 小呆呆
 * @create 2019-07-01 23:45
 **/
@RestController
@RequestMapping("kafka")
public class SendController {

    @Autowired
    private Producer producer;
    @RequestMapping(value = "send")
    public String send() {
        producer.sendMessage(new Message());
        return "{\"code\":0}";
    }

}
