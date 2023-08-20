package com.learn.demo.mall.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
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
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * oauth2授权相关信息的自定义配置
 * @author zh_cr
 */
@Configuration
public class AuthorizationCommonConfig {

    /**
     * oauth2数据源
     */
    @Autowired
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

    /**
     * 加载私钥配置
     */
    @Bean("keyProp")
    public KeyProperties keyProperties(){
        return new KeyProperties();
    }

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    /**
     * 使用私钥配置JWT
     */
    @Bean
    public JwtAccessTokenConverter oauthJwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties.getKeyStore().getLocation(),  // 私钥位置
                keyProperties.getKeyStore().getPassword().toCharArray())  // 私钥库
                .getKeyPair(
                        keyProperties.getKeyStore().getAlias(),  //私钥别名
                        keyProperties.getKeyStore().getSecret().toCharArray());  //私钥密码
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

    /**
     * REST请求模板类
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
