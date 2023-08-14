package com.learn.demo.mall.gateway.filter;

import com.learn.demo.mall.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 权限校验过滤
 * @author zh_cr
 */
@Component
@Slf4j
public class AuthorizedFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${filter.auth.loginUrls:}")
    private List<String> loginUrls;

    @Value("${jwt.header:token}")
    private String tokenHeaderName;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 放行登录请求
        if (Objects.nonNull(loginUrls) && loginUrls.contains(request.getURI().getPath())) {
            return chain.filter(exchange);
        }

        // 获取并验证token
        String token = request.getHeaders().getFirst(tokenHeaderName);

        if (StringUtils.isNotBlank(token)) {
            try {
                Claims claims = jwtUtil.parseToken(token);
                log.info("鉴权成功，token签发时间: " + claims.getIssuedAt());
                // 鉴权成功
                return chain.filter(exchange);
            } catch (Exception e) {
                // 鉴权失败：token不合法
                log.warn(e.getMessage(), e);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }

        // 鉴权失败：token为空
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
