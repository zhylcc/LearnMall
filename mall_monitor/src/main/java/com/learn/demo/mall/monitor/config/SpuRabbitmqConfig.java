package com.learn.demo.mall.monitor.config;

import com.learn.demo.mall.monitor.enums.SpuMarketableRoutingKeyEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Spu相关的rabbitmq
 * @author zh_cr
 */
@Configuration
public class SpuRabbitmqConfig {

    // 交换机
    public static final String SPU_MARKETABLE_EXCHANGE = "spuMarketableExchange";  // 商品上、下架

    // 消息队列
    public static final String INDEX_BATCH_IMPORT_QUEUE = "indexBatchImportQueue";  // 批量导入相关sku索引
    public static final String INDEX_BATCH_DELETE_QUEUE = "indexBatchDeleteQueue";  // 批量删除相关sku索引

    @Bean(INDEX_BATCH_IMPORT_QUEUE)
    public Queue indexBatchImportQueue() {
        return new Queue(INDEX_BATCH_IMPORT_QUEUE);
    }

    @Bean(INDEX_BATCH_DELETE_QUEUE)
    public Queue indexBatchDeleteQueue() {
        return new Queue(INDEX_BATCH_DELETE_QUEUE);
    }


    @Bean(SPU_MARKETABLE_EXCHANGE)
    public Exchange spuMarketableExchange() {
        return ExchangeBuilder.directExchange(SPU_MARKETABLE_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding binding4IndexBatchImport(@Qualifier(INDEX_BATCH_IMPORT_QUEUE) Queue queue, @Qualifier(SPU_MARKETABLE_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SpuMarketableRoutingKeyEnum.UP.getKey()).noargs();
    }

    @Bean
    public Binding binding4IndexBatchDelete(@Qualifier(INDEX_BATCH_DELETE_QUEUE) Queue queue, @Qualifier(SPU_MARKETABLE_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SpuMarketableRoutingKeyEnum.DOWN.getKey()).noargs();
    }

}
