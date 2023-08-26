package com.learn.demo.mall.pay.controller;

import com.learn.demo.mall.common.enums.BasicErrorCodeEnum;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.pay.response.ApplyOrderResp;
import com.learn.demo.mall.pay.response.CloseOrderResp;
import com.learn.demo.mall.pay.response.QueryOrderResp;
import com.learn.demo.mall.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("/apply")
    public Result<ApplyOrderResp> applyOrder(@RequestParam String orderId, @RequestParam Integer amount) {
        return Result.success(payService.applyOrder(orderId, amount));
    }

    @PostMapping("/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        payService.handleNotify(request);
        try {
            response.setContentType("text/html");
            response.getWriter().write("ok");
        } catch (IOException e) {
            throw new BaseBizException("回调处理失败", BasicErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public Result<QueryOrderResp> queryOrderById(@PathVariable String orderId) {
        return Result.success(payService.queryOrderById(orderId));
    }

    @PutMapping("/{orderId}")
    public Result<CloseOrderResp> closeOrderById(@PathVariable String orderId) {
        return Result.success(payService.closeOrderById(orderId));
    }
}
