package com.learn.demo.mall.common.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 消息发送数据
 *
 * @author zh_cr
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendData implements Serializable {

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 路由key（交换机为""时可指定为队列）
     */
    private String routingKey;

    /**
     * 消息内容
     */
    private String messageBody;
}
