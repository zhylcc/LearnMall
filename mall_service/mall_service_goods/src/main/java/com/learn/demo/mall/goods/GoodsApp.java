package com.learn.demo.mall.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 商品微服务启动类
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"},
        exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class}
)
@EnableEurekaClient
@MapperScan(basePackages = {"com.learn.demo.mall.goods.dao"})
@Slf4j
public class GoodsApp {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApp.class, args);
        log.info("GoodsApp Start Successfully!");
    }
}
