package com.learn.demo.mall.system.enums;

import com.learn.demo.mall.common.exception.ErrorCode;

/**
 * @author zh_cr
 */
public enum SystemErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(20000, "系统服务参数不合法"),
    THIRD_SERVICE_CALL_EXCEPTION(20001, "系统服务中三方服务调用异常"),
    BIZ_ADMIN_WARNING(20100, "管理员用户管理异常提示"),
    BIZ_ADMIN_NOT_EXIST(20101, "管理员用户名不存在"),
    BIZ_ADMIN_MISMATCH(20102, "管理员用户名密码不匹配"),
    ;

    private final Integer code;

    private final String message;

    SystemErrorCodeEnum(Integer code, String message) {
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
