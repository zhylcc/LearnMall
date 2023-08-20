package com.learn.demo.mall.goods.feign;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.pojo.SpuPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zh_cr
 */
@FeignClient(name = "goods")
public interface SpuFeign {

    @GetMapping("/goods/spu/{id}")
    Result<SpuPO> queryById(@PathVariable String id);
}
