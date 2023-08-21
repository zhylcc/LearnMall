package com.learn.demo.mall.gateway.filter;

import lombok.extern.slf4j.Slf4j;
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
 * 过滤黑名单
 * @author zh_cr
 */
@Deprecated
@Component
@Slf4j
public class BlackListFilter implements GlobalFilter, Ordered {

    @Value("${filter.blackList:}")
    private List<String> blackList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String address = getClientIp(request);
        if (Objects.nonNull(blackList) && blackList.contains(address)) {
            log.warn("拦截ip: " + address);
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
