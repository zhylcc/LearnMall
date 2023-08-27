package com.learn.demo.mall.seckill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 秒杀服务
 * @author zh_cr
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@MapperScan(basePackages = {"com.learn.demo.mall.seckill.dao"})
@Slf4j
public class SeckillApp {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApp.class, args);
        log.info("SeckillApp Start Successfully!");
    }
}
