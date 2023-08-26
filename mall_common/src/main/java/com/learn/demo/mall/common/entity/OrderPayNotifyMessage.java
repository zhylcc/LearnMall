package com.learn.demo.mall.common.entity;

import lombok.*;

/**
 * 订单支付回调消息
 *
 * @author zh_cr
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayNotifyMessage {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 流水号
     */
    private String transactionId;
}
