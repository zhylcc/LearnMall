package com.learn.demo.mall.web.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.order.feign.CartFeign;
import com.learn.demo.mall.order.feign.OrderFeign;
import com.learn.demo.mall.order.response.ListCartResp;
import com.learn.demo.mall.user.feign.AddressFeign;
import com.learn.demo.mall.web.response.WOrderResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/web/order")
public class WOrderController {

    @Resource
    private AddressFeign addressFeign;

    @Resource
    private CartFeign cartFeign;

    @Resource
    private OrderFeign orderFeign;

    @GetMapping
    public Result<WOrderResp> wOrder() {
        WOrderResp order = new WOrderResp();
        order.setAddress(addressFeign.queryByUsername().getData());
        ListCartResp cartData = cartFeign.list().getData();
        order.setCart(cartData.getItems());
        order.setTotal(cartData.getTotal());
        order.setMoney(cartData.getMoney());
        return Result.success(order);
    }

    @PostMapping("/apply")
    public Result<String> apply() {
        return orderFeign.add();
    }
}
