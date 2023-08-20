package com.learn.demo.mall.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * 授权服务器配置
 * @author zh_cr
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientDetailsService oauthClientDetailsService;

    @Autowired
    private UserDetailsServiceConfig oauthUserDetailService;

    @Autowired
    private TokenStore oauthTokenStore;

    @Autowired
    private PasswordEncoder oauthPasswordEncoder;

    @Autowired
    private JwtAccessTokenConverter oauthJwtAccessTokenConverter;

    @Autowired
    private AuthenticationManager oauthAuthenticationManager;

    /**
     * 配置授权端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(oauthJwtAccessTokenConverter) // 授权令牌转化
                .tokenStore(oauthTokenStore)  // 授权令牌持久化（存在JWT中）
                .authenticationManager(oauthAuthenticationManager)  // 依赖SpringSecurity支持密码模式
                .userDetailsService(oauthUserDetailService);
    }


    /**
     * 授权服务器安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients()  // 允许客户端使用表单验证<client_id, client_secret>
                .passwordEncoder(oauthPasswordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 授权客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("mall")
//                .secret(passwordEncoder().encode("123456"))
//                .scopes("app")
//                .authorizedGrantTypes("password","authorization_code", "refresh_token", "client_credentials");
        clients.jdbc(dataSource).clients(oauthClientDetailsService); // 根据数据库中动态配置
    }
}
