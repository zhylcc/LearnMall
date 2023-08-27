package com.learn.demo.mall.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 秒杀下单消息队列配置
 *
 * @author zh_cr
 */
@Deprecated
@Configuration
public class SeckillOrderMqConfig {

    public static final String SECKILL_ORDER_QUEUE = "seckillOrderQueue";

    @Bean(SECKILL_ORDER_QUEUE)
    public Queue seckillOrderQueue() {
        // 队列持久化
        return new Queue(SECKILL_ORDER_QUEUE, true);
    }
}
