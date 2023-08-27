package com.learn.demo.mall.seckill.consumer;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.enums.BasicErrorCodeEnum;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.seckill.pojo.SeckillOrderPO;
import com.learn.demo.mall.seckill.service.SeckillOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 秒杀下单消息消费者
 * @author zh_cr
 */
@Component
@Slf4j
public class SeckillOrderConsumer {

    @Resource
    private SeckillOrderService seckillOrderService;

    @RabbitListener(queues = KeyConfigUtil.SECKILL_ORDER_QUEUE)
    public void reveiveSeckillOrderMessage(Channel channel, Message message) {
        SeckillOrderPO seckillOrder = JSON.parseObject(message.getBody(), SeckillOrderPO.class);
        log.info("接收到秒杀下单异步消息, 订单id：" + seckillOrder.getId());

        // 1. 秒杀订单同步落库
        String seckillOrderId = seckillOrderService.apply(seckillOrder);

        // 2. 手动应答
        if (!StringUtils.isEmpty(seckillOrderId)) {
            // 消费者返回成功应答
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new BaseBizException("MQ消费成功应答异常", BasicErrorCodeEnum.UNKNOWN_ERROR);
            }
        } else {
            // 消费者返回失败应答，重新入队
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new BaseBizException("MQ消费失败应答异常", BasicErrorCodeEnum.UNKNOWN_ERROR);
            }
        }

        log.info("秒杀异步下单完成, 订单id：" + seckillOrder.getId());
    }
}
