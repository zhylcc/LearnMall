package com.learn.demo.mall.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 下单增加用户积分：创建队列、交换机并绑定
 *
 * @author zh_cr
 */
@Deprecated
@Configuration
public class OrderAndAddPointsMqConfig {

    // 交换机
    public static final String USER_ADD_POINTS_EXCHANGE = "userAddPointsExchange";  // 用户增加积分

    // 消息队列
    public static final String TO_ADD_POINTS_QUEUE = "toAddPointsQueue";  // 增加积分
    public static final String FINISH_ADD_POINTS_QUEUE = "finishAddPointsQueue";  // 增加积分完成

    // 路由key
    public static final String TO_ADD_POINTS_KEY = "to";
    public static final String FINISH_ADD_POINTS_KEY = "finish";

    @Bean(TO_ADD_POINTS_QUEUE)
    public Queue toAddPointsQueue() {
        return new Queue(TO_ADD_POINTS_QUEUE);
    }

    @Bean(FINISH_ADD_POINTS_QUEUE)
    public Queue finishAddPointsQueue() {
        return new Queue(FINISH_ADD_POINTS_QUEUE);
    }


    @Bean(USER_ADD_POINTS_EXCHANGE)
    public Exchange userAddPointsExchange() {
        return ExchangeBuilder.directExchange(USER_ADD_POINTS_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding binding4ToAddPointsQueue(@Qualifier(TO_ADD_POINTS_QUEUE) Queue queue, @Qualifier(USER_ADD_POINTS_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TO_ADD_POINTS_KEY).noargs();
    }

    @Bean
    public Binding binding4FinishAddPointsQueue(@Qualifier(FINISH_ADD_POINTS_QUEUE) Queue queue, @Qualifier(USER_ADD_POINTS_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(FINISH_ADD_POINTS_KEY).noargs();
    }
}
