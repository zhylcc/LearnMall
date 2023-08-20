package com.learn.demo.mall.order.service;

import com.learn.demo.mall.common.util.SnowflakeIdUtil;
import com.learn.demo.mall.goods.feign.SkuFeign;
import com.learn.demo.mall.order.dao.OrderItemMapper;
import com.learn.demo.mall.order.dao.OrderMapper;
import com.learn.demo.mall.order.enums.*;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import com.learn.demo.mall.order.pojo.OrderPO;
import com.learn.demo.mall.order.response.ListCartResp;
import com.learn.demo.mall.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zh_cr
 */
@Service
public class OrderService {

    @Value("${cart-key-prefix}")
    private String cartKeyPrefix;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private UserFeign userFeign;

    public String add(OrderPO order) {
        ListCartResp cartData = cartService.list(order.getUsername());
        order.setTotalNum(cartData.getTotal());
        order.setTotalMoney(cartData.getMoney());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(order.getCreateTime());
        order.setBuyerRate(BuyerRateEnum.NOT_RATE.getValue());
        order.setOrderStatus(OrderStatusEnum.NOT_COMPLETED.getValue());
        order.setPayStatus(PayStatusEnum.PAYING.getValue());
        order.setConsignStatus(ConsignStatusEnum.NOT_SHIPPED.getValue());
        order.setId(String.valueOf(snowflakeIdUtil.nextId()));
        // 插入订单表
        orderMapper.insertSelective(order);
        List<OrderItemPO> items = cartData.getItems();
        items.forEach(item->{
            item.setId(String.valueOf(snowflakeIdUtil.nextId()));
            item.setIsReturn(ReturnStatusEnum.NOT_RETURNED.getValue());
            item.setOrderId(order.getId());
            // 插入订单明细
            orderItemMapper.insertSelective(item);
        });
        // 减库存
        skuFeign.reduce(order.getUsername());
        // 增加积分
        userFeign.updatePoints(order.getTotalMoney());
        // 清除购物车缓存
        redisTemplate.delete(cartKeyPrefix + order.getUsername());
        // 返回订单ID
        return order.getId();
    }
}
