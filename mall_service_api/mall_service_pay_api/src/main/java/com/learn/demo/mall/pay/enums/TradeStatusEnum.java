package com.learn.demo.mall.pay.enums;

/**
 * 订单支付状态
 * @author zh_cr
 */

public enum TradeStatusEnum {
    SUCCESS("SUCCESS", "支付成功"),
    NOT_PAY("NOT_PAY", "未支付"),
    ;

    private final String status;

    private final String message;

    TradeStatusEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static TradeStatusEnum of(String status) {
        for (TradeStatusEnum value : TradeStatusEnum.values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }
}
