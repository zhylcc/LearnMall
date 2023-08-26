package com.learn.demo.mall.order.enums;

import com.learn.demo.mall.common.exception.ErrorCode;

/**
 * @author zh_cr
 */

public enum OrderErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(40000, "订单服务参数不合法"),
    GOODS_NOT_EXIST(40001, "购物车商品不存在"),
    PAY_SERVICE_INTERFACE_EXCEPTION(40002, "支付服务接口异常"),
    SKU_SERVICE_INTERFACE_EXCEPTION(40003, "商品sku接口异常"),

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
