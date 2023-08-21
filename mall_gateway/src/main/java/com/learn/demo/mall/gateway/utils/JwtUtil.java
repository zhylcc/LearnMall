package com.learn.demo.mall.gateway.utils;

import com.learn.demo.mall.gateway.config.GatewayConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * JWT工具类
 * @author zh_cr
 */
@Component
public class JwtUtil {

    @Resource
    private GatewayConfig gatewayConfig;

    /**
     * 创建token
     * @param id 唯一ID
     * @param subject 主题
     * @return token
     */
    public String createToken(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + gatewayConfig.getJwtTimeout();
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("mall")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(expDate);
        return builder.compact();
    }

    public Claims parseToken(String token) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(gatewayConfig.getJwtKey());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}