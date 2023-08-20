package com.learn.demo.mall.auth;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void createJwt() {
        // 获取私钥
        String privateKeyLocation = "key/mall.jks";
        String storePass = "storepass";
        String alias = "mall";
        String keyPass = "keypass";
        ClassPathResource privateKeyResource = new ClassPathResource(privateKeyLocation);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(privateKeyResource, storePass.toCharArray());
        RSAPrivateKey privateKey = (RSAPrivateKey) keyStoreKeyFactory.getKeyPair(alias, keyPass.toCharArray()).getPrivate();
        // 生成token
        Map<String, String> payload = new HashMap<>();
        payload.put("area", "beijing");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));
        System.out.println(jwt.getEncoded());
    }

    @Test
    public void parseJwt() throws IOException {
        // 获取公钥
        String publicKeyLocation = "public.key";
        ClassPathResource publicKeyResource = new ClassPathResource(publicKeyLocation);
        InputStream inputStream = publicKeyResource.getInputStream();
        String publicKey = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // 解析token
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcmVhIjoiYmVpamluZyJ9.U_8F0oKVznMfwpRaraM6j-hpX_zhG9Ex807dOmo8DyMvWP4lmZEmnoY3pQvp5GTaFEuiZqmfhRqGkF5mWGwH0gTBPlQJaUT06VhDx_zu5N6SPCxiF8aKbCV0A58JkgmZOgAlgBL6ZzDeWlVBCDsZV6_7_WmhIH1sHw2IoYk7H0Dx8LMNV3Yj0ZW_FroRY8mfcJh4EsmXzUziwuTv-xd21Zn71GRSrXZNCgkeWbVwpqMh8R3hocAP6915if_ZnPx7MhgblLtPs5X4SW5PH6tvpeD7A5aP-X4fE9id-SZ2q3S9XFbzYELcp8ch_7wZKEfHvFMUjKeolX_wbZhkRSbSew";
        String claims = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey)).getClaims();
        System.out.println(claims);
    }


    @Test
    public void checkPw() {
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
        System.out.println(BCrypt.checkpw("123456", "$2a$10$59RJPKDaup0932iPDPUqd.KmGaMkbMpexmvRuPRk000UhuDVoylSO"));
    }

    @Test
    public void test() {
        String a = null;
        String b = null;
        System.out.println(StringUtils.isNotBlank(a));
        System.out.println(a+b);
    }
}
