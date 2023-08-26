package com.learn.demo.mall.order.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订单支付消息队列
 *
 * @author zh_cr
 */
@Deprecated
@Configuration
public class OrderPayMqConfig {

    public static final String ORDER_PAY_QUEUE = "orderPayQueue";

    @Bean(ORDER_PAY_QUEUE)
    public Queue orderPayQueue() {
        return new Queue(ORDER_PAY_QUEUE);
    }
}
