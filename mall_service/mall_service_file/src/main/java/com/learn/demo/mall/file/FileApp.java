package com.learn.demo.mall.file;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.github.tobato.fastdfs.FdfsClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * 文件微服务
 * @author zh_cr
 */
@SpringBootApplication(
        scanBasePackages = {"com.learn.demo.mall"},
        exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class}
)
@EnableEurekaClient
@Import(FdfsClientConfig.class)
@Slf4j
public class FileApp {

    public static void main(String[] args) {
        SpringApplication.run(FileApp.class, args);
        log.info("FileApp Start Successfully!");
    }
}
