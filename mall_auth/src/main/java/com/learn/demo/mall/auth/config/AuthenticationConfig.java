package com.learn.demo.mall.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * oauth2授权相关信息的自定义配置
 * @author zh_cr
 */
@Configuration
@ConfigurationProperties("authentication")
@Getter
@Setter
public class AuthenticationConfig {

    /**
     * 加载私钥配置
     */
    private KeyStore keyStore;

    /**
     * 放行url列表
     */
    private String[] whiteList;

    /**
     * 用户授权
     */
    private String[] authorities;

    /**
     * oauth2数据源
     */
    @Resource
    private DataSource dataSource;

    /**
     * 密码的加密方式
     */
    @Bean
    public PasswordEncoder oauthPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 授权客户端信息
     */
    @Bean
    public ClientDetailsService oauthClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Getter
    @Setter
    public static class KeyStore {

        private org.springframework.core.io.Resource location;

        private String storePass;

        private String alias;

        private String keyPass;
    }

    /**
     * 使用私钥配置JWT
     */
    @Bean
    public JwtAccessTokenConverter oauthJwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyStore.getLocation(),  // 私钥位置
                keyStore.getStorePass().toCharArray())  // 私钥库
                .getKeyPair(
                        keyStore.getAlias(),  //私钥别名
                        keyStore.getKeyPass().toCharArray());  //私钥密码
        converter.setKeyPair(keyPair);
        return converter;
    }

    /**
     * 授权令牌信息
     */
    @Bean
    public TokenStore oauthTokenStore() {
        return new JwtTokenStore(oauthJwtAccessTokenConverter());
    }
}
