package com.learn.demo.mall.pay.service;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.entity.OrderPayNotifyMessage;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.pay.enums.TradeStatusEnum;
import com.learn.demo.mall.pay.request.NotifyReq;
import com.learn.demo.mall.pay.response.ApplyOrderResp;
import com.learn.demo.mall.pay.response.CloseOrderResp;
import com.learn.demo.mall.pay.response.QueryOrderResp;
import com.learn.demo.mall.pay.utils.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zh_cr
 */
@Service
@Slf4j
public class PayService {

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private RabbitTemplate rabbitTemplate;


    public ApplyOrderResp applyOrder(String orderId, Integer amount) {
        // todo 仅模拟
        ApplyOrderResp resp = new ApplyOrderResp();
        resp.setOrderId(orderId);
        resp.setAmount(amount);
        return resp;
    }

    public void handleNotify(HttpServletRequest request) {
        NotifyReq notifyReq = parseNotifyReq(request);
        if (!TradeStatusEnum.SUCCESS.getStatus().equals(notifyReq.getReturnCode())) {
            log.warn(notifyReq.getErrorMessage());
            return;
        }
        QueryOrderResp queryOrderResp = queryOrderById(notifyReq.getOrderId());
        // 发送订单消息
        OrderPayNotifyMessage message = OrderPayNotifyMessage.builder()
                .orderId(queryOrderResp.getOrderId())
                .transactionId(queryOrderResp.getTransactionId())
                .build();
        rabbitTemplate.convertAndSend("", KeyConfigUtil.ORDER_PAY_QUEUE, JSON.toJSONString(message));

    }

    public QueryOrderResp queryOrderById(String orderId) {
        // todo 仅模拟
        QueryOrderResp resp = new QueryOrderResp();
        resp.setOrderId(orderId);
        resp.setTransactionId(String.valueOf(snowflakeIdUtil.nextId()));
        resp.setTradeStatus(Objects.requireNonNull(TradeStatusEnum.of(KeyConfigUtil.getTestTradeStatus())).getStatus());
        return resp;
    }

    public CloseOrderResp closeOrderById(String orderId) {
        // todo 仅模拟
        CloseOrderResp resp = new CloseOrderResp();
        resp.setOrderId(orderId);
        return resp;
    }

    private NotifyReq parseNotifyReq(HttpServletRequest request) {
        // todo 仅模拟
        NotifyReq req = new NotifyReq();
        req.setReturnCode(TradeStatusEnum.SUCCESS.getStatus());
        req.setReturnMessage(TradeStatusEnum.SUCCESS.getMessage());
        req.setErrorMessage("");
        req.setOrderId(KeyConfigUtil.getTestOrderId());
        return req;
    }
}
