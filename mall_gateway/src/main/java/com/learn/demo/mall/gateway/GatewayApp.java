package com.learn.demo.mall.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 网关微服务
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"}
)
@EnableEurekaClient
@Slf4j
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
        log.info("GatewayApp Start Successfully!");
    }
}
