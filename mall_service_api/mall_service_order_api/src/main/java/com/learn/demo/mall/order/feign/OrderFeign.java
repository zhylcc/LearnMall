package com.learn.demo.mall.order.feign;

import com.learn.demo.mall.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zh_cr
 */
@FeignClient(name = "order")
public interface OrderFeign {

    @PostMapping("/order/info/apply")
    Result<String> add();
}
