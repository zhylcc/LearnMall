package com.learn.demo.mall.monitor;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * canal数据监控微服务
 * @author zh_cr
 */
@SpringBootApplication
@EnableCanalClient
@Slf4j
public class CanalApp {

    public static void main(String[] args) {
        SpringApplication.run(CanalApp.class, args);
        log.info("CanalMonitorApp Start Successfully!");
    }
}
