package com.learn.demo.mall.auth.controller;

import com.learn.demo.mall.auth.config.AuthenticationConfig;
import com.learn.demo.mall.auth.enums.AuthErrorCodeEnum;
import com.learn.demo.mall.auth.request.AuthLoginReq;
import com.learn.demo.mall.auth.service.AuthService;
import com.learn.demo.mall.auth.utils.CookieUtil;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * 授权
 * @author zh_cr
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private AuthenticationConfig authenticationConfig;

    @PostMapping("/login")
    public Result<String> login(@RequestBody AuthLoginReq req, HttpServletResponse response) {
        if (StringUtils.isBlank(req.getClientId()) || StringUtils.isBlank(req.getClientSecret())) {
            throw new BaseBizException("HttpBasic认证失败：客户端ID或密码为空！", AuthErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (StringUtils.isBlank(req.getUsername()) || StringUtils.isBlank(req.getPassword())) {
            throw new BaseBizException("用户认证失败：用户名或密码为空！", AuthErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        // 申请令牌并缓存到redis：jti->access_token
        String jti = authService.login(req);
        // 设置cookie：jti
        CookieUtil.addCookie(response, authenticationConfig.getCookieDomain(), "/", "jti", jti, authenticationConfig.getCookieMaxAge(), false);
        return Result.success(jti);
    }
}
