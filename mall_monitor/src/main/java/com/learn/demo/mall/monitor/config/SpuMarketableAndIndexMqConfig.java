package com.learn.demo.mall.monitor.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 商品上下架与索引同步：创建队列、交换机并绑定
 *
 * @author zh_cr
 */
@Deprecated
@Configuration
public class SpuMarketableAndIndexMqConfig {

    // 交换机
    public static final String SPU_MARKETABLE_EXCHANGE = "spuMarketableExchange";  // 商品上、下架

    // 消息队列
    public static final String INDEX_IMPORT_QUEUE = "indexImportQueue";  // 批量导入相关sku索引
    public static final String INDEX_DELETE_QUEUE = "indexDeleteQueue";  // 批量删除相关sku索引

    // 路由key
    public static final String INDEX_IMPORT_KEY = "up";
    public static final String INDEX_DELETE_KEY = "down";

    @Bean(INDEX_IMPORT_QUEUE)
    public Queue indexBatchImportQueue() {
        return new Queue(INDEX_IMPORT_QUEUE);
    }

    @Bean(INDEX_DELETE_QUEUE)
    public Queue indexBatchDeleteQueue() {
        return new Queue(INDEX_DELETE_QUEUE);
    }


    @Bean(SPU_MARKETABLE_EXCHANGE)
    public Exchange spuMarketableExchange() {
        return ExchangeBuilder.directExchange(SPU_MARKETABLE_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding binding4IndexBatchImport(@Qualifier(INDEX_IMPORT_QUEUE) Queue queue, @Qualifier(SPU_MARKETABLE_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(INDEX_IMPORT_KEY).noargs();
    }

    @Bean
    public Binding binding4IndexBatchDelete(@Qualifier(INDEX_DELETE_QUEUE) Queue queue, @Qualifier(SPU_MARKETABLE_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(INDEX_DELETE_KEY).noargs();
    }
}
