package com.learn.demo.mall.order.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.learn.demo.mall.common.entity.JwtClaims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zh_cr
 */
@Component
public class TokenDecodeUtil {

    @Resource(name = "publicKey")
    private String publicKey;

    /***
     * 解析Token信息
     */
    public JwtClaims parseClaims(){
        //获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌校验并解码
        Jwt jwt = JwtHelper.decodeAndVerify(details.getTokenValue(), new RsaVerifier(publicKey));
        //获取Jwt原始内容
        String claims = jwt.getClaims();
        Map<String, String> map = JSON.parseObject(claims, new TypeReference<HashMap<String, String>>(){});
        return JwtClaims.builder().username(map.get("user_name"))
                .jti(map.get("jti"))
                .clientId(map.get("client_id"))
                .exp(Integer.valueOf(map.get("exp")))
                .scope(JSONArray.parseArray(map.get("scope"), String.class))
                .authorities(JSONArray.parseArray(map.get("authorizes"), String.class))
                .build();
    }

}
