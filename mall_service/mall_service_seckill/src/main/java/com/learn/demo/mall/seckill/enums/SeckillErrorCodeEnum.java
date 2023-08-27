package com.learn.demo.mall.seckill.enums;

import com.learn.demo.mall.common.exception.ErrorCode;

/**
 * @author zh_cr
 */

public enum SeckillErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(60000, "秒杀服务参数不合法"),
    COMMIT_ORDER_FAIL(60001, "秒杀下单失败"),
    COMMIT_GOODS_FROZEN(60002, "商品秒杀冷却中"),
    COMMIT_GOODS_REPEAT(60003, "已参与过该商品秒杀"),
    COMMIT_GOODS_LACK(60004, "秒杀商品库存不足"),
    RATE_LIMITER_BLOCKED(60005, "限流阻止访问")
    ;

    private final Integer code;

    private final String message;

    SeckillErrorCodeEnum(Integer code, String message) {
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
