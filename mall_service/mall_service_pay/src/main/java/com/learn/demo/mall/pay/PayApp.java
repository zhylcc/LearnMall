package com.learn.demo.mall.pay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 支付服务（todo 仅模拟，后续考虑接入微信或支付宝沙箱）
 * @author zh_cr
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class PayApp {

    public static void main(String[] args) {
        SpringApplication.run(PayApp.class, args);
        log.info("PayApp Start Successfully!");
    }
}
