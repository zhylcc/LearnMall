package com.learn.demo.mall.order.dao;

import com.learn.demo.mall.order.pojo.OrderItemPO;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Component
public interface OrderItemMapper extends Mapper<OrderItemPO> {
}
