package com.learn.demo.mall.gateway.filter;

import com.learn.demo.mall.gateway.config.CustomFilterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 过滤黑名单（ip）
 * @author zh_cr
 */
@Component
@Slf4j
public class BlackListFilter implements GlobalFilter, Ordered {

    @Autowired
    private CustomFilterConfig filterConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String address = getClientIp(request);
        List<String> blackIps = filterConfig.getBlackIps();
        if (Objects.nonNull(blackIps) && blackIps.contains(address)) {
            log.warn("filtered ip: " + address);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }


    private String getClientIp(ServerHttpRequest request) {
        String ipAddress = request.getHeaders().getFirst("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            try {
                ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
            } catch (NullPointerException e) {
                return null;
            }
        }

        // If the IP address contains multiple entries separated by commas, use the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
