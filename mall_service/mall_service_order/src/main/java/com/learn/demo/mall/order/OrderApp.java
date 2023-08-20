package com.learn.demo.mall.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 订单服务
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"}
//        , exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class}
)
@EnableEurekaClient
@MapperScan(basePackages = {"com.learn.demo.mall.order.dao"})
@EnableFeignClients(basePackages = {"com.learn.demo.mall.goods.feign", "com.learn.demo.mall.user.feign"})
@Slf4j
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
        log.info("OrderApp Start Successfully!");
    }
}
