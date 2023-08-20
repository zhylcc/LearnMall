package com.learn.demo.mall.order.feign;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.order.response.ListCartResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zh_cr
 */
@FeignClient(name = "order")
public interface CartFeign {

    @GetMapping("/order/cart")
    Result<ListCartResp> list();
}
