package com.learn.demo.mall.pay.feign;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.pay.response.CloseOrderResp;
import com.learn.demo.mall.pay.response.QueryOrderResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author zh_cr
 */
@FeignClient(name = "pay")
public interface PayFeign {

    @GetMapping("/pay/{orderId}")
    Result<QueryOrderResp> queryOrderById(@PathVariable String orderId);

    @PutMapping("/pay/{orderId}")
    Result<CloseOrderResp> closeOrderById(@PathVariable String orderId);
}
