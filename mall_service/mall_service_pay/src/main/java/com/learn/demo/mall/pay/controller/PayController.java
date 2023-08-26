package com.learn.demo.mall.pay.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.pay.response.CloseOrderResp;
import com.learn.demo.mall.pay.response.QueryOrderResp;
import com.learn.demo.mall.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Resource
    private PayService payService;

    @GetMapping("/{orderId}")
    public Result<QueryOrderResp> queryOrderById(@PathVariable String orderId) {
        return Result.success(payService.queryOrderById(orderId));
    }

    @PutMapping("/{orderId}")
    public Result<CloseOrderResp> closeOrderById(@PathVariable String orderId) {
        return Result.success(payService.closeOrderById(orderId));
    }
}
