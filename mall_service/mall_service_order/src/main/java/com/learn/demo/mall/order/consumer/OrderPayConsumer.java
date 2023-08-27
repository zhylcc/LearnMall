package com.learn.demo.mall.order.consumer;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.entity.OrderPayNotifyMessage;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 订单支付消息消费者
 * @author zh_cr
 */
@Component
@Slf4j
public class OrderPayConsumer {

    @Resource
    private OrderService orderService;

    @RabbitListener(queues = KeyConfigUtil.ORDER_PAY_QUEUE)
    public void receiveOrderPayMessage(String message) {
        OrderPayNotifyMessage orderPayNotifyMessage = JSON.parseObject(message, OrderPayNotifyMessage.class);
        log.info("接收到订单支付消息：" + orderPayNotifyMessage.getOrderId());
        // 修改订单状态为已支付
        orderService.updatePayStatus4Success(orderPayNotifyMessage.getOrderId(), orderPayNotifyMessage.getTransactionId());
        log.info("订单状态已更新：" + orderPayNotifyMessage.getOrderId());
    }
}
