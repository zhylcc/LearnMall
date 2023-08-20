package com.learn.demo.mall.auth.config;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.user.pojo.UserPO;
import com.learn.demo.mall.user.feign.UserFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.Objects;

/**
 * 自定义用户（客户端）详情服务
 *
 * @author zh_cr
 */
@Configuration
public class UserDetailsServiceConfig implements UserDetailsService {

    @Value("${authentication.user-authorities:}")
    private String authoritiesCommaString;

    @Autowired
    private ClientDetailsService oauthClientDetailsService;

    @Autowired
    private UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 1. 优先使用客户端信息验证<client_id, client_secret>
        if (Objects.isNull(authentication)) {
            ClientDetails clientDetails = oauthClientDetailsService.loadClientByClientId(username);
            if (Objects.nonNull(clientDetails)) {
                // 客户端只需要完成身份认证，不需要权限
                return new User(username, clientDetails.getClientSecret(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 2. 用户认证
        Result<UserPO> result = userFeign.loadByUsername(username);
        if (Objects.isNull(result) || Objects.isNull(result.getData())) {
            return null;
        }
        return new User(username, result.getData().getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesCommaString));
    }
}
