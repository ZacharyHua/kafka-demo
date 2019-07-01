package com.study.kafkademo.TestController;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author 小呆呆
 * @create 2019-06-29 15:00
 **/
//@ControllerAdvice(basePackages = "com.study.kafka")
public class TestController implements ResponseBodyAdvice<String> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //这里直接返回true,表示对任何handler的responsebody都调用beforeBodyWrite方法
        System.out.println("22222222222222222222");

        return true;
    }

    @Override
    public String beforeBodyWrite(String resBody, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("11111111111111111111111"+resBody);

        //resBody就是controller方法中返回的值，对其进行修改后再return就可以了
        resBody.toString().replaceAll("(?:http://portal.bee360.io|http://103.229.116.92:51009)", "http://portal.bee360.com.cn");
        resBody.toString().replaceAll("(?:http://admin.bee360.io|http://103.229.116.92:51001)", "http://admin.bee360.com.cn");

//        resBody.toString().replace("http://portal.bee360.io","http://portal.bee360.com.cn");
//        resBody.toString().replace("http://admin.bee360.io","http://admin.bee360.com.cn");
//        resBody.toString().replace("http://103.229.116.92:51009","http://portal.bee360.com.cn");
//        resBody.toString().replace("http://103.229.116.92:51001","http://admin.bee360.com.cn");
        return resBody;
    }
}
