package com.learn.demo.mall.order.enums;

/**
 * 订单评价状态
 * @author zh_cr
 */

public enum BuyerRateEnum {
    NOT_RATE("0", "未评价"),
    RATED("1", "已评价"),
    ;

    private final String value;

    BuyerRateEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
