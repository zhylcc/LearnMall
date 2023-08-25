package com.learn.demo.mall.system.utils;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * JWT工具类
 * @author zh_cr
 */
@Getter
@Setter
@Component
public class JwtUtil {

    /**
     * 有效期，ms
     */
    private static final Long EXPIRE = 300000L;

    /**
     * 创建token
     * @param id 唯一ID
     * @param subject 主题
     * @return token
     */
    public String createToken(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + EXPIRE;
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
        byte[] encodedKey = Base64.getDecoder().decode(KeyConfigUtil.getSystemJwtKey());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}