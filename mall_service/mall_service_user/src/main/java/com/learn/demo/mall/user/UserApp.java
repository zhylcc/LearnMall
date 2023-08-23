package com.learn.demo.mall.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 用户微服务
 * @author zh_cr
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.learn.demo.mall.user.dao")
@Slf4j
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
        log.info("UserApp Start Successfully!");
    }
}
