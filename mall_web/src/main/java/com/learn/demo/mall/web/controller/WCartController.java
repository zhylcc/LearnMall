package com.learn.demo.mall.web.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.order.feign.CartFeign;
import com.learn.demo.mall.order.response.ListCartResp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/web/cart")
public class WCartController {

    @Resource
    private CartFeign cartFeign;

    @GetMapping
    public Result<ListCartResp> list() {
        return cartFeign.list();
    }
}
