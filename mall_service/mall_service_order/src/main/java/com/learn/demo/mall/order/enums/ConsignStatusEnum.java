package com.learn.demo.mall.order.enums;

/**
 * 订单物流状态
 * @author zh_cr
 */

public enum ConsignStatusEnum {
    NOT_SHIPPED("0", "未发货"),
    SHIPPED("1", "已发货"),
    RECEIVED("2", "已收货"),
    ;

    private final String value;

    ConsignStatusEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
