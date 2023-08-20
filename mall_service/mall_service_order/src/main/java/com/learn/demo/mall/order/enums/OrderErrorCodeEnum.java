package com.learn.demo.mall.order.enums;

import com.learn.demo.mall.common.enums.ErrorCode;

/**
 * @author zh_cr
 */

public enum OrderErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(40000, "订单服务参数不合法"),
    GOODS_NOT_EXIST(40001, "购物车商品不存在"),

    ;

    private final Integer code;

    private final String message;

    OrderErrorCodeEnum(Integer code, String message) {
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
