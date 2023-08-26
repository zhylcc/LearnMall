package com.learn.demo.mall.order.enums;

/**
 * 订单状态
 * @author zh_cr
 */

public enum OrderStatusEnum {
    NOT_COMPLETED("0", "未完成"),
    COMPLETED("1", "已完成"),
    RETURNED("2", "已退货"),
    CLOSED("3", "已关闭"),
    ;

    private final String value;

    OrderStatusEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
