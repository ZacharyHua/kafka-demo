package com.study.kafkademo.producer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 消息模板
 * @author 小呆呆
 * @create 2019-07-01 23:51
 **/
@Data
@Accessors(chain = true)
public class Message {
    private String id;

    private String msg;

    private Date sendTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
