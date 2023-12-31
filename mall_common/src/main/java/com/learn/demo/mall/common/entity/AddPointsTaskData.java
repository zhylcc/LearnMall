package com.learn.demo.mall.common.entity;

import lombok.*;

/**
 * 下单增加用户积分，任务数据
 *
 * @author zh_cr
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPointsTaskData {

    /**
     * 下单用户
     */
    private String username;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 增加积分数
     */
    private Integer points;
}
