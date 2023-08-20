package com.learn.demo.mall.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web安全配置
 * @author zh_cr
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 放行url
    @Value("${authentication.ignoring-urls:}")
    private String[] ignoringUrls;

    /**
     * 认证管理器
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * http安全配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // 关闭跨域保护（允许跨域访问）
                .httpBasic() // 启用HttpBasic身份验证
                .and().formLogin()  // 允许表单登录
                .and().authorizeRequests().anyRequest().authenticated();  // 限制请求需要授权
    }

    /**
     * 资源放行
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ignoringUrls);
    }

}
