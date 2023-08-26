package com.learn.demo.mall.pay.response;

import lombok.Data;

/**
 * 下单响应
 * @author zh_cr
 */
@Data
public class ApplyOrderResp {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 金额，分
     */
    private Integer amount;
}
