package com.learn.demo.mall.goods.enums;

import com.learn.demo.mall.common.enums.ErrorCode;

/**
 * @author zh_cr
 */

public enum GoodsErrorCodeEnum implements ErrorCode {

    UNKNOWN_ERROR(10001, "未归类异常"),
    BIZ_WARNING(10002, "业务异常提示"),
    ARGUMENT_MISMATCH(10003, "参数为空或类型不匹配"),
    ARGUMENT_ILLEGAL(10004, "参数不合法"),
    DATABASE_ERROR(10005, "数据库异常"),
    SQL_EXCEPTION(10006, "SQL执行异常"),
    ;

    private final Integer code;

    private final String message;

    GoodsErrorCodeEnum(Integer code, String message) {
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
