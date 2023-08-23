package com.learn.demo.mall.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * FastDFS分布式文件存储
 *
 * @author zh_cr
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class FastDFSClientApp {

    public static void main(String[] args) {
        SpringApplication.run(FastDFSClientApp.class, args);
        log.info("FastDFSClientApp Start Successfully!");
    }
}
