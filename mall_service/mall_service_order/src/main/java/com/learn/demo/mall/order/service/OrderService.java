package com.learn.demo.mall.order.service;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.goods.feign.SkuFeign;
import com.learn.demo.mall.order.dao.OrderItemMapper;
import com.learn.demo.mall.order.dao.OrderMapper;
import com.learn.demo.mall.order.enums.*;
import com.learn.demo.mall.order.pojo.OrderExtPO;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import com.learn.demo.mall.order.pojo.OrderPO;
import com.learn.demo.mall.order.response.ListCartResp;
import com.learn.demo.mall.order.utils.SnowflakeIdUtil;
import com.learn.demo.mall.user.feign.UserFeign;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author zh_cr
 */
@Service
public class OrderService {

    private static final String CART_KEY_PREFIX = KeyConfigUtil.getCartKeyPrefix();

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private CartService cartService;

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private SkuFeign skuFeign;

    @Resource
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
        redisTemplate.delete(CART_KEY_PREFIX + order.getUsername());
        // 返回订单ID
        return order.getId();
    }

    public OrderExtPO queryById(String id) {
        OrderPO order = orderMapper.selectByPrimaryKey(id);
        if (Objects.isNull(order)) {
            return null;
        }
        OrderItemPO example = new OrderItemPO();
        example.setOrderId(id);
        List<OrderItemPO> items = orderItemMapper.select(example);
        OrderExtPO orderExt = new OrderExtPO();
        orderExt.setOrder(order);
        orderExt.setItems(items);
        return orderExt;
    }
}
