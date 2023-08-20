package com.learn.demo.mall.auth.enums;

import com.learn.demo.mall.common.enums.ErrorCode;

/**
 * @author zh_cr
 */

public enum AuthErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(90001, "授权服务参数不合法"),
    TOKEN_APPLY_FAIL(90002, "申请令牌失败"),
    ;

    private final Integer code;

    private final String message;

    AuthErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
