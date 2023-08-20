package com.learn.demo.mall.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 授权认证后的令牌实体
 * @author zh_cr
 */
@Getter
@Setter
@Builder
public class AuthToken implements Serializable {

    // 接入令牌
    private String accessToken;

    // 刷新令牌
    private String refreshToken;

    // 短令牌
    private String jti;

    public boolean valid() {
        return StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(refreshToken) && StringUtils.isNotBlank(jti);
    }
}
