package com.learn.demo.mall.user.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 用户登录配置
 * @author zh_cr
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("login")
public class UserLoginConfig {

    /**
     * jwt在redis中的过期时间
     */
    private Integer jwtTimeout;

    /**
     * jti cookie作用域
     */
    private String cookieDomain;

    /**
     * jti cookie过期时间
     */
    private Integer cookieMaxAge;


    /**
     * REST请求模板类
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
