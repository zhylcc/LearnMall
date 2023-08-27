package com.learn.demo.mall.order.service;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.entity.AddPointsTaskData;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.goods.feign.SkuFeign;
import com.learn.demo.mall.order.pojo.OrderLogPO;
import com.learn.demo.mall.order.dao.OrderItemMapper;
import com.learn.demo.mall.order.dao.OrderLogMapper;
import com.learn.demo.mall.order.dao.OrderMapper;
import com.learn.demo.mall.order.dao.TaskMapper;
import com.learn.demo.mall.order.enums.*;
import com.learn.demo.mall.order.pojo.OrderExtPO;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import com.learn.demo.mall.order.pojo.OrderPO;
import com.learn.demo.mall.order.pojo.TaskPO;
import com.learn.demo.mall.order.response.ListCartResp;
import com.learn.demo.mall.order.utils.AuthorizationUtil;
import com.learn.demo.mall.order.utils.SnowflakeIdUtil;
import com.learn.demo.mall.pay.enums.TradeStatusEnum;
import com.learn.demo.mall.pay.feign.PayFeign;
import com.learn.demo.mall.pay.response.QueryOrderResp;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    private OrderLogMapper orderLogMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private CartService cartService;

    @Resource
    private SkuFeign skuFeign;

    @Resource
    private PayFeign payFeign;

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Transactional
    public String apply(OrderPO order) {
        // 补充订单信息
        ListCartResp cartData = cartService.list(order.getUsername());
        order.setTotalNum(cartData.getTotal());
        order.setTotalMoney(cartData.getMoney());
        order.setCreateTime(new Date());
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

        // 增加积分（基于消息队列实现分布式事务）
//        userFeign.updatePoints(order.getTotalMoney());
        AddPointsTaskData taskData = AddPointsTaskData.builder()
                .username(order.getUsername())
                .orderId(order.getId())
                .points(order.getTotalMoney())
                .build();
        TaskPO task = TaskPO.builder()
                .id(snowflakeIdUtil.nextId())
                .createTime(new Date())
                .updateTime(new Date())
                .mqExchange(KeyConfigUtil.USER_ADD_POINTS_EXCHANGE)
                .mqRoutingkey(KeyConfigUtil.TO_ADD_POINTS_KEY)
                .requestBody(JSON.toJSONString(taskData))
                .build();
        taskMapper.insertSelective(task);

        // 清除购物车缓存
        redisTemplate.delete(CART_KEY_PREFIX + order.getUsername());

        // 发送订单延迟消息（需要传递授权信息，否则消费方调用feign肯会401）
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader(HttpHeaders.AUTHORIZATION, AuthorizationUtil.getAuthorizationHeader());
            return message;
        };
        rabbitTemplate.convertAndSend("", KeyConfigUtil.ORDER_CREATE_QUEUE, order.getId(), messagePostProcessor);

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

    @Transactional
    public void checkAndCloseOrder(String id) throws Exception {
        // 1. 查询数据库当前订单状态
        OrderPO order = orderMapper.selectByPrimaryKey(id);
        if (Objects.isNull(order) || !OrderStatusEnum.NOT_COMPLETED.getValue().equals(order.getOrderStatus())) {
            return;
        }
        // 2. 从支付服务查询订单状态
        Result<QueryOrderResp> queryOrderRespResult = payFeign.queryOrderById(id);
        if (Objects.isNull(queryOrderRespResult) || Objects.isNull(queryOrderRespResult.getData())) {
            throw new BaseBizException("订单支付状态查询失败", OrderErrorCodeEnum.PAY_SERVICE_INTERFACE_EXCEPTION);
        }
        QueryOrderResp orderResp = queryOrderRespResult.getData();
        if (TradeStatusEnum.SUCCESS.getStatus().equals(orderResp.getTradeStatus())) {
            // 2.1 已支付，进行数据补偿
            // 修改订单为支付成功状态
            updatePayStatus4Success(id, orderResp.getTransactionId());
        }
        else {
            // 2.2 未支付
            // 修改订单为关闭状态
            order.setUpdateTime(new Date());
            order.setOrderStatus(OrderStatusEnum.CLOSED.getValue());
            orderMapper.updateByPrimaryKeySelective(order);
            // 记录订单日志
            OrderLogPO orderLog = OrderLogPO.builder()
                    .id(String.valueOf(snowflakeIdUtil.nextId()))
                    .operater("automatic")
                    .operateTime(new Date())
                    .orderStatus(OrderStatusEnum.CLOSED.getValue())
                    .orderId(Long.valueOf(id))
                    .build();
            orderLogMapper.insert(orderLog);
            // 恢复商品库存
            OrderItemPO example = new OrderItemPO();
            example.setOrderId(id);
            List<OrderItemPO> items = orderItemMapper.select(example);
            items.forEach(item->{
                skuFeign.resume(item.getSkuId(), item.getNum());
            });
            // 手动触发支付服务关闭订单
            payFeign.closeOrderById(id);
        }
    }

    @Transactional
    public void updatePayStatus4Success(String orderId, String transactionId) {
        OrderPO order = orderMapper.selectByPrimaryKey(orderId);
        if (Objects.isNull(order) || !PayStatusEnum.PAYING.getValue().equals(order.getPayStatus())) {
            return;
        }
        order.setPayStatus(PayStatusEnum.SUCCESS.getValue());
        order.setOrderStatus(OrderStatusEnum.COMPLETED.getValue());
        order.setUpdateTime(new Date());
        order.setPayTime(new Date());
        order.setTransactionId(transactionId);
        orderMapper.updateByPrimaryKeySelective(order);
        // 记录订单日志
        OrderLogPO orderLog = OrderLogPO.builder()
                .id(String.valueOf(snowflakeIdUtil.nextId()))
                .operater("automatic")
                .operateTime(new Date())
                .orderStatus(OrderStatusEnum.COMPLETED.getValue())
                .payStatus(PayStatusEnum.SUCCESS.getValue())
                .remarks(transactionId)
                .orderId(Long.valueOf(orderId))
                .build();
        orderLogMapper.insert(orderLog);
    }
}
