package com.learn.demo.mall.user.enums;

import com.learn.demo.mall.common.exception.ErrorCode;

/**
 * @author zh_cr
 */

public enum UserErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(30000, "用户服务参数不合法"),
    TOKEN_APPLY_FAIL(30001, "登录申请令牌失败"),
    USER_NOT_EXIST(30002, "用户不存在"),
    ;

    private final Integer code;

    private final String message;

    UserErrorCodeEnum(Integer code, String message) {
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
