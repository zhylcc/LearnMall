package com.learn.demo.mall.user.service;

import com.learn.demo.mall.common.entity.AuthToken;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.user.config.UserLoginConfig;
import com.learn.demo.mall.user.dao.UserMapper;
import com.learn.demo.mall.user.enums.UserErrorCodeEnum;
import com.learn.demo.mall.user.pojo.UserPO;
import com.learn.demo.mall.user.request.UserLoginReq;
import com.learn.demo.mall.user.utils.CookieUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zh_cr
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserLoginConfig userLoginConfig;


    public void login(UserLoginReq req, HttpServletResponse response) throws IOException {
        String applyUrl = loadBalancerClient.choose(KeyConfigUtil.getAuthInstanceId()).getUri() + "/oauth/token";
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
        Map data = restTemplate.exchange(applyUrl, HttpMethod.POST, request, Map.class).getBody();
        if (Objects.isNull(data)) {
            throw new BaseBizException("申请令牌失败！", UserErrorCodeEnum.TOKEN_APPLY_FAIL);
        }
        AuthToken authToken = AuthToken.builder()
                .accessToken((String) data.get("access_token"))
                .refreshToken((String) data.get("refresh_token"))
                .jti((String) data.get("jti"))
                .build();
        if (!authToken.valid()) {
            throw new BaseBizException("申请令牌失败！", UserErrorCodeEnum.TOKEN_APPLY_FAIL);
        }
        // 申请令牌并缓存到redis：jti->access_token
        stringRedisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(), userLoginConfig.getJwtTimeout(), TimeUnit.SECONDS);
        // 设置cookie：jti
        CookieUtil.addCookie(response, userLoginConfig.getCookieDomain(), "/", KeyConfigUtil.getJtiCookieName(), authToken.getJti(), userLoginConfig.getCookieMaxAge(), false);
        if (KeyConfigUtil.isRedirect()) {
            // 跳转到首页,GET
            response.setStatus(HttpStatus.SEE_OTHER.value());
            response.sendRedirect(KeyConfigUtil.getWebDomain() + "/web/access/index");
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 删除cookie：jti
        String jti = CookieUtil.deleteCookie(request, response, KeyConfigUtil.getJtiCookieName());
        // 删除redis缓存
        if (!StringUtils.isEmpty(jti)) {
            stringRedisTemplate.delete(jti);
        }
        if (KeyConfigUtil.isRedirect()) {
            // 重定向到登录页,GET
            response.setStatus(HttpStatus.SEE_OTHER.value());
            response.sendRedirect(KeyConfigUtil.getWebDomain() + "/web/access/toLogin");
        }
    }

    public UserPO queryByUsername(String username) {
        UserPO user = userMapper.selectByPrimaryKey(username);
        if (Objects.isNull(user)) {
            throw new BaseBizException("用户不存在", UserErrorCodeEnum.USER_NOT_EXIST);
        }
        return user;
    }

    public void deleteByUsername(String username) {
        userMapper.deleteByPrimaryKey(username);
    }

    public void updatePoints(String username, Integer points) {
        UserPO user = userMapper.selectByPrimaryKey(username);
        user.setPoints(user.getPoints() + points);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
