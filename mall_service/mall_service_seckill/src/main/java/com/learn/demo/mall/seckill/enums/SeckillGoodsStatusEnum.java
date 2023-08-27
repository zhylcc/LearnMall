package com.learn.demo.mall.seckill.enums;

/**
 * 秒杀订单状态
 * @author zh_cr
 */

public enum SeckillGoodsStatusEnum {
    NON_APPROVED("0", "待审核"),
    APPROVED("1", "审核通过"),
    ;

    private final String value;

    SeckillGoodsStatusEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
