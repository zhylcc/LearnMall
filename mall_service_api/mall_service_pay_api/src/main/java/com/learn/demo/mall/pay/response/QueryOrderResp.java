package com.learn.demo.mall.pay.response;

import com.learn.demo.mall.pay.enums.TradeStatusEnum;
import lombok.Data;

/**
 * 查询订单返回响应
 *
 * @author zh_cr
 */
@Data
public class QueryOrderResp {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付状态
     * @see TradeStatusEnum
     */
    private String tradeStatus;

    /**
     * 流水号
     */
    private String transactionId;
}
