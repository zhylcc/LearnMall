package com.learn.demo.mall.order.enums;

/**
 * 订单支付状态
 * @author zh_cr
 */

public enum PayStatusEnum {
    PAYING("0", "未支付"),
    SUCCESS("1", "已支付"),
    FAIL("2", "支付失败"),
    ;

    private final String value;

    PayStatusEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
