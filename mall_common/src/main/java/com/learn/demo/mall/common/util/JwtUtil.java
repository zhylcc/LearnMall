package com.learn.demo.mall.common.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    // 加密算法
    @Value("${jwt.signatureAlg:HS256}")
    private String signatureAlg;

    //有效期，ms
    @Value("${jwt.expire:300000}")
    private Long expire;

    //设置秘钥明文
    @Value("${jwt.secretKey:mallJwt}")
    private String secretKey;

    /**
     * 创建token
     * @param id 唯一ID
     * @param subject 主题
     * @return token
     */
    public String createToken(String id, String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(signatureAlg);
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + expire;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("mall")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
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
        byte[] encodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}