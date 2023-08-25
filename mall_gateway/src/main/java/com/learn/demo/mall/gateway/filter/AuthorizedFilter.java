package com.learn.demo.mall.gateway.filter;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.gateway.config.GatewayConfig;
import com.learn.demo.mall.gateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 权限校验过滤
 * @author zh_cr
 */
@Component
@Slf4j
public class AuthorizedFilter implements GlobalFilter, Ordered {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private GatewayConfig gatewayConfig;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_VALUE_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 放行登录请求
        if (!KeyConfigUtil.isNeedAuthorized() || this.checkInWhiteList(request.getURI().getPath())) {
            return chain.filter(exchange);
        }

        // 1. 系统管理服务鉴权
        // 获取并验证token
         String token = request.getHeaders().getFirst(KeyConfigUtil.getTokenHeader());
        if (StringUtils.isNotBlank(token)) {
            try {
                Claims claims = jwtUtil.parseToken(token);
                log.info("鉴权成功，token签发时间: " + claims.getIssuedAt());
                // 鉴权成功
                return chain.filter(exchange);
            } catch (Exception e) {
                // 鉴权失败：token不合法
                log.warn(e.getMessage(), e);
                return authenticationFail(response);
            }
        }

        // 2. oauth授权服务鉴权
        // 获取cookie中的jti
        String jti = this.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)) {
            return authenticationFail(response);
        }
        // 获取redis中的jwt
        String jwt = this.getJwtFromRedis(jti);
        if (StringUtils.isNotBlank(jwt)) {
            request.mutate().header(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE_PREFIX+jwt);
            log.info("授权信息设置成功，jti: " + jti);
            return chain.filter(exchange);
        }

        return authenticationFail(response);
    }

    private boolean checkInWhiteList(String path) {
        String[] whiteList = gatewayConfig.getWhiteList();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pattern: whiteList) {
            if (antPathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String getJtiFromCookie(ServerHttpRequest request) {
        HttpCookie cookie = request.getCookies().getFirst(KeyConfigUtil.getJtiCookieName());
        if (Objects.isNull(cookie)) {
            return null;
        }
        return cookie.getValue();
    }

    private String getJwtFromRedis(String jti) {
        return stringRedisTemplate.boundValueOps(jti).get();
    }

    private Mono<Void> authenticationFail(ServerHttpResponse response) {
        // 鉴权失败
        if (KeyConfigUtil.isRedirect()) {
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().set("Location", KeyConfigUtil.getWebDomain() + "/web/user/toLogin");
        }
        else {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        }
        return response.setComplete();
    }

}
