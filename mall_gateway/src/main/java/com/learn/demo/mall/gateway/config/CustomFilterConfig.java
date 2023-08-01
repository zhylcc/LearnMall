package com.learn.demo.mall.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 自定义过滤器相关配置
 * @author zh_cr
 */
@Configuration
@ConfigurationProperties(prefix = "custom-filter")
public class CustomFilterConfig {

    private List<String> blackIps;

    private List<String> loginUrls;

    private String tokenHeaderName;

    public void setBlackIps(List<String> blackIps) {
        this.blackIps = blackIps;
    }

    public List<String> getBlackIps() {
        return blackIps;
    }

    public void setLoginUrls(List<String> loginUrls) {
        this.loginUrls = loginUrls;
    }

    public List<String> getLoginUrls() {
        return loginUrls;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
    }
}
