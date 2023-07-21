package com.learn.demo.mall.goods.controller;


import com.learn.demo.mall.common.enums.StatusCodeEnum;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.exception.GoodsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zh_cr
 */
@RestController
@RequestMapping("/goods/exception")
public class GoodsExceptionController {

    @PostMapping
    public Result<String> handle() {
        throw new GoodsException("API测试商品微服务统一异常", StatusCodeEnum.UNKNOWN_ERROR);
    }
}
