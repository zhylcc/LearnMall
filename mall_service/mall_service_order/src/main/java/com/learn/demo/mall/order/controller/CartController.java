package com.learn.demo.mall.order.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.order.request.AddCartReq;
import com.learn.demo.mall.order.response.ListCartResp;
import com.learn.demo.mall.order.service.CartService;
import com.learn.demo.mall.order.utils.TokenDecodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/order/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private TokenDecodeUtil tokenDecodeUtil;

    @PutMapping
    public Result<String> add(@RequestBody AddCartReq req) {
        String username = tokenDecodeUtil.parseClaims().getUsername();
        req.setUsername(username);
        return Result.success(cartService.add(req));
    }

    @GetMapping
    public Result<ListCartResp> list() {
        String username = tokenDecodeUtil.parseClaims().getUsername();
        return Result.success(cartService.list(username));
    }
}
