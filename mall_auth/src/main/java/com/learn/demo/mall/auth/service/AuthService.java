package com.learn.demo.mall.auth.service;

import com.learn.demo.mall.auth.config.AuthenticationConfig;
import com.learn.demo.mall.auth.enums.AuthErrorCodeEnum;
import com.learn.demo.mall.auth.request.AuthLoginReq;
import com.learn.demo.mall.common.entity.AuthToken;
import com.learn.demo.mall.common.exception.BaseBizException;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 授权服务
 * @author zh_cr
 */
@Service
public class AuthService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AuthenticationConfig authenticationConfig;


    public String login(AuthLoginReq req) {
        String applyUrl = loadBalancerClient.choose(authenticationConfig.getInstanceId()).getUri() + "/oauth/token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password"); // 密码方式请求令牌
        body.add("username", req.getUsername());
        body.add("password", req.getPassword());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(
                "Authorization",
                "Basic " + new String(Base64Utils.encode((req.getClientId()+":"+req.getClientSecret()).getBytes()))
        );
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(applyUrl, HttpMethod.POST, request, Map.class);
        Map data = response.getBody();
        if (Objects.isNull(data)) {
            throw new BaseBizException("申请令牌失败！", AuthErrorCodeEnum.TOKEN_APPLY_FAIL);
        }
        AuthToken authToken = AuthToken.builder()
                .accessToken((String) data.get("access_token"))
                .refreshToken((String) data.get("refresh_token"))
                .jti((String) data.get("jti"))
                .build();
        if (!authToken.valid()) {
            throw new BaseBizException("申请令牌失败！", AuthErrorCodeEnum.TOKEN_APPLY_FAIL);
        }
        stringRedisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(), authenticationConfig.getJwtTimeout(), TimeUnit.SECONDS);
        return authToken.getJti();
    }
}
