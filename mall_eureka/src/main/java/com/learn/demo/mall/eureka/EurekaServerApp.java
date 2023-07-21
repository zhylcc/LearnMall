package com.learn.demo.mall.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka注册中心-服务端
 * @author zh_cr
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaServerApp.class);

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
        LOGGER.info("Eureka Server Start Successfully!");
    }
}
