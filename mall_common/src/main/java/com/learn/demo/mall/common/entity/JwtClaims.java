package com.learn.demo.mall.common.entity;

import lombok.*;

import java.util.List;

/**
 * @author zh_cr
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtClaims {

    /**
     * user_name
     */
    private String username;

    private String jti;

    /**
     * client_id
     */
    private String clientId;

    /**
     * 过期时间戳
     */
    private Integer exp;

    private List<String> scope;

    private List<String> authorities;
}
