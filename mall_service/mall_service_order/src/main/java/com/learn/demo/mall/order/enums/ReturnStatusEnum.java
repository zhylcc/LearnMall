package com.learn.demo.mall.order.enums;

/**
 * 订单是否退货
 * @author zh_cr
 */

public enum ReturnStatusEnum {
    NOT_RETURNED("0", "未退货"),
    RETURNED("1", "退货"),
    ;

    private final String value;

    ReturnStatusEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
