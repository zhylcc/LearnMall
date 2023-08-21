package com.learn.demo.mall.common.enums;

/**
 * 错误异常码
 * @author zh_cr
 */

public enum BasicErrorCodeEnum implements ErrorCode {
    UNKNOWN_ERROR(-1, "未知异常"),
    PARAM_ERROR(-2, "参数错误"),
    ;

    private final Integer code;

    private final String message;

    BasicErrorCodeEnum(Integer code, String message) {
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
