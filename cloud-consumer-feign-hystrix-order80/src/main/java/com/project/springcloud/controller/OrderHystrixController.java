package com.project.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.project.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value ="/consumer/payment/hystrix/ok/{id}")
    public String paymentInfoOK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfoOK(id);
        return  result;
    }


//    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })
    @GetMapping(value ="/consumer/payment/hystrix/ok/{id}")
    @HystrixCommand
    public String paymentInfoTimeOut(Integer id){
        String result = paymentHystrixService.paymentInfoTimeOut(id);
        return  result;
    }

    public String  paymentInfoTimeOutHandler(Integer id){
        return "消费者80,对方支付系统繁忙,请等待或者自己运行出错";
    }

    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息,请稍后再试: orz~";
    }
}
