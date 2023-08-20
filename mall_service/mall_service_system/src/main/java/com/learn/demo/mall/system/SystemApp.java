package com.learn.demo.mall.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 系统微服务
 *
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"},
        exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class}
)
@EnableEurekaClient
@MapperScan(basePackages = {"com.learn.demo.mall.system.dao"})
@Slf4j
public class SystemApp {

    public static void main(String[] args) {
        SpringApplication.run(SystemApp.class, args);
        log.info("SystemApp Start Successfully!");
    }
}
