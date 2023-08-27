package com.learn.demo.mall.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.entity.MessageSendData;
import com.learn.demo.mall.common.enums.BasicErrorCodeEnum;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

/**
 * confirm机制
 *
 * @author zh_cr
 */
@Component
@Slf4j
public class MessageConfirmUtil implements RabbitTemplate.ConfirmCallback {

    @Resource
    private RabbitTemplate  rabbitTemplate;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public MessageConfirmUtil(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (Objects.isNull(correlationData) || Objects.isNull(correlationData.getId())) {
            throw new BaseBizException("MQ消息发送确认异常", BasicErrorCodeEnum.UNKNOWN_ERROR);
        }
        log.info("处理MQ消息发送确认：" + correlationData.getId());
        if (ack) {
            // 1. 成功，删除redis缓存数据
            // 消息唯一标识
//            redisTemplate.delete(correlationData.getId());
            // 消息发送记录
            redisTemplate.delete(KeyConfigUtil.getSeckillConfirmKeyPrefix() + correlationData.getId());
        } else {
            // 失败，重新向队列发送消息
            MessageSendData sendData = JSON.parseObject((String) redisTemplate.boundValueOps(KeyConfigUtil.getSeckillConfirmKeyPrefix() + correlationData.getId()).get(), MessageSendData.class);
            if (Objects.isNull(sendData)) {
                throw new BaseBizException("MQ获取重发消息异常", BasicErrorCodeEnum.UNKNOWN_ERROR);
            }
            rabbitTemplate.convertAndSend(sendData.getExchange(), sendData.getRoutingKey(), JSON.toJSONString(sendData.getMessageBody()));
        }
    }

    public void sendMessageWithConfirm(String exchange, String routingKey, String message) {
        // 缓存消息唯一标识
        String id = UUID.randomUUID().toString();
//        redisTemplate.boundValueOps(id).set(message);

        // 缓存消息发送数据
        MessageSendData sendData = MessageSendData.builder()
                .exchange(exchange)
                .routingKey(routingKey)
                .messageBody(message)
                .build();
        redisTemplate.boundValueOps(KeyConfigUtil.getSeckillConfirmKeyPrefix() + id).set(JSON.toJSONString(sendData));

        // 携带唯一标识发送消息
        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }
}
