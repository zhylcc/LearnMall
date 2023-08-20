package com.learn.demo.mall.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 授权服务器
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"}
)
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.learn.demo.mall.user.feign")
@Slf4j
public class OauthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(OauthServerApp.class, args);
        log.info("OauthServerApp Start Successfully!");
    }
}
