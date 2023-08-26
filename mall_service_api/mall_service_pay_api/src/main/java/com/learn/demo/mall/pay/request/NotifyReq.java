package com.learn.demo.mall.pay.request;

import lombok.Data;

/**
 * 回调请求参数
 * @author zh_cr
 */
@Data
public class NotifyReq {

    /**
     * 返回响应码
     */
    private String returnCode;

    /**
     * 返回消息
     */
    private String returnMessage;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 订单号
     */
    private String orderId;
}
