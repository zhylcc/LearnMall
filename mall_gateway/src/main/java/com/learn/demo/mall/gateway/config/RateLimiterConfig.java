package com.learn.demo.mall.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * 限流配置
 * @author zh_cr
 */
@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver ipKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
                String ip = "";
                if (Objects.nonNull(remoteAddress)) {
                    ip = remoteAddress.getHostName();
                }
                return Mono.just(ip);
            }
        };
    }
}
