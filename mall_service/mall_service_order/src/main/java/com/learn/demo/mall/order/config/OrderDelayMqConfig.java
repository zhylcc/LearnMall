package com.learn.demo.mall.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订单延迟消息配置
 * @author zh_cr
 */
@Deprecated
@Configuration
public class OrderDelayMqConfig {

    // 交换机
    public static final String ORDER_TIMEOUT_EXCHANGE = "orderTimeoutExchange";

    // 队列
    public static final String ORDER_TIMEOUT_QUEUE = "orderTimeoutQueue";
    public static final String ORDER_CREATE_QUEUE = "orderCreateQueue";

    // 参数
    public static final Long ARGS_MESSAGE_TTL = 10000L;  // 消息超时时间


    @Bean(ORDER_TIMEOUT_QUEUE)
    public Queue orderTimeoutQueue() {
        return new Queue(ORDER_TIMEOUT_QUEUE);
    }

    @Bean(ORDER_CREATE_QUEUE)
    public Queue orderCreateQueue() {
        return QueueBuilder.durable(ORDER_CREATE_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_TIMEOUT_EXCHANGE)
                .withArgument("x-message-ttl", ARGS_MESSAGE_TTL)
                .build();
    }

    @Bean(ORDER_TIMEOUT_EXCHANGE)
    public Exchange orderTimeoutExchange() {
        return ExchangeBuilder.fanoutExchange(ORDER_TIMEOUT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding binding4OrderTimeoutQueue(@Qualifier(ORDER_TIMEOUT_QUEUE) Queue queue, @Qualifier(ORDER_TIMEOUT_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}
