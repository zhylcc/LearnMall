package com.learn.demo.mall.order.pojo;

import lombok.*;

import java.util.List;

/**
 * @author zh_cr
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderExtPO {

    private OrderPO order;

    private List<OrderItemPO> items;
}
