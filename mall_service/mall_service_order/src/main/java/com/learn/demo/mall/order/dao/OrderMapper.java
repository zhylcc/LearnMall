package com.learn.demo.mall.order.dao;

import com.learn.demo.mall.order.pojo.OrderPO;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Component
public interface OrderMapper extends Mapper<OrderPO> {
}
