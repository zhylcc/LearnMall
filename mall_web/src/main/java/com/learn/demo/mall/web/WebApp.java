package com.learn.demo.mall.web;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 前台（页面渲染）服务，本项目中可视为测试项目
 *
 * 接入网关但没有接入oauth2
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"},
        exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class}
)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.learn.demo.mall.order.feign", "com.learn.demo.mall.user.feign"})
@Slf4j
public class WebApp {

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
        log.info("WebApp Start Successfully!");
    }
}
