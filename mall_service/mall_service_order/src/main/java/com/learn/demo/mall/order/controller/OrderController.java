package com.learn.demo.mall.order.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.order.pojo.OrderExtPO;
import com.learn.demo.mall.order.pojo.OrderPO;
import com.learn.demo.mall.order.service.OrderService;
import com.learn.demo.mall.order.utils.TokenDecodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 订单管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/order/info")
public class OrderController {

    @Resource
    private TokenDecodeUtil tokenDecodeUtil;

    @Resource
    private OrderService orderService;

    @PostMapping("/apply")
    public Result<String> add() {
        String username = tokenDecodeUtil.parseClaims().getUsername();
        OrderPO order = new OrderPO();
        order.setUsername(username);
        return Result.success(orderService.apply(order));
    }

    @GetMapping("/{id}")
    public Result<OrderExtPO> queryById(@PathVariable String id) {
        return Result.success(orderService.queryById(id));
    }
}
