package com.learn.demo.mall.common.enums;

import com.learn.demo.mall.common.response.StatusCode;

/**
 * @author zh_cr
 */

public enum StatusCodeEnum implements StatusCode {

    SUCCESS(0, "成功"),
    UNKNOWN_ERROR(-1, "系统异常，未知错误");

    private final Integer code;

    private final String message;

    StatusCodeEnum(Integer code, String message) {
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
