package com.learn.demo.mall.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 商品微服务启动类
 * @author zh_cr
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class GoodsApp {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApp.class, args);
        log.info("GoodsApp Start Successfully!");
    }
}
