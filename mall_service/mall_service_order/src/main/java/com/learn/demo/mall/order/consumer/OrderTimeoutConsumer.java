package com.learn.demo.mall.order.consumer;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.order.entity.NonWebRequestAttributes;
import com.learn.demo.mall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;

/**
 * 订单超时消息消费者
 * @author zh_cr
 */
@Component
@Slf4j
public class OrderTimeoutConsumer {

    @Resource
    private OrderService orderService;

    @RabbitListener(queues = KeyConfigUtil.ORDER_TIMEOUT_QUEUE)
    public void receiveOrderTimeoutMessage(Message message) {
        String orderId = new String(message.getBody());
        String authorization = (String) message.getMessageProperties().getHeaders().get(HttpHeaders.AUTHORIZATION);
        NonWebRequestAttributes requestAttributes = new NonWebRequestAttributes();
        requestAttributes.setAttribute(HttpHeaders.AUTHORIZATION, authorization, 0);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        log.info("接收到订单超时消息：" + orderId);
        // 关闭订单
        try {
            orderService.checkAndCloseOrder(orderId);
            log.info("超时订单已关闭：" + orderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        RequestContextHolder.resetRequestAttributes();
    }
}
