package com.learn.demo.mall.goods.feign;


import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.pojo.SkuPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zh_cr
 */
@FeignClient(name = "goods")
public interface SkuFeign {

    @GetMapping("/goods/sku/{id}")
    Result<SkuPO> queryById(@PathVariable String id);

    @PostMapping("/goods/sku/reduce")
    Result<Integer> reduce(@RequestParam String username);
}
