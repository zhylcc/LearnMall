package com.learn.demo.mall.user.request;

import lombok.Data;

/**
 * 授权登录请求
 * @author zh_cr
 */
@Data
public class UserLoginReq {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密码（用于HttpBasic认证）
     */
    private String clientSecret;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（用于用户认证）
     */
    private String password;
}
