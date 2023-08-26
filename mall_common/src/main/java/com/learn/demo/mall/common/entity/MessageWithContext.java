package com.learn.demo.mall.common.entity;

import lombok.*;

/**
 * 携带上下文的消息
 * @author zh_cr
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageWithContext {

    /**
     * mq消息
     */
    private String message;

    /**
     * 上下文
     */
    private String context;
}
