package com.study.kafkademo.TestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 小呆呆
 * @create 2019-06-29 15:09
 **/
//@RestController
//@RequestMapping("test")
public class controller {

    @ResponseBody
    @RequestMapping("test")
    public String test( @RequestParam(value = "keyword", required = true) String keyword){
        System.out.println("111111111111111111111111");
        return "http://admin.bee360.io/FtpUpload/kc_news/2019-06-14/CKqZcKp80pX7xxRuh7Se_1560491267507_.jpg108.jpg";
    }
}
