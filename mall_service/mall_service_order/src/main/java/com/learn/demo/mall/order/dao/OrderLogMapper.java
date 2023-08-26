package com.learn.demo.mall.order.dao;

import com.learn.demo.mall.order.pojo.OrderLogPO;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Component
public interface OrderLogMapper extends Mapper<OrderLogPO> {
}
