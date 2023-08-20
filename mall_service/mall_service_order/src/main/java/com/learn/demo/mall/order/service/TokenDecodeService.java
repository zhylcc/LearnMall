package com.learn.demo.mall.order.service;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.entity.JwtClaims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zh_cr
 */
@Service
public class TokenDecodeService {

    @Resource(name = "publicKey")
    private String publicKey;

    /***
     * 获取用户信息
     */
    public Map<String,String> getUserInfo(){
        //获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌解码
        return readToken(details.getTokenValue());
    }

    /***
     * 读取令牌数据
     * @see JwtClaims
     */
    private Map<String,String> readToken(String token){
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        //获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims,Map.class);
    }
}
