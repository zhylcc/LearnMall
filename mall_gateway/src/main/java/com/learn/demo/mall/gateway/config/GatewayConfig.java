package com.learn.demo.mall.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * 限流配置
 * @author zh_cr
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("gateway")
public class GatewayConfig {

    /**
     * jwt过期时间，ms
     */
    private Long jwtTimeout = 300000L;

    /**
     * 黑名单
     */
    private String[] blackList;

    /**
     * 白名单
     */
    private String[] whiteList;

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
            String ip = "";
            if (Objects.nonNull(remoteAddress)) {
                ip = remoteAddress.getHostName();
            }
            return Mono.just(ip);
        };
    }
}
