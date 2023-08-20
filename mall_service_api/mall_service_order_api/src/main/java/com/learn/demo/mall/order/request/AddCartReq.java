package com.learn.demo.mall.order.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 添加购物车请求
 * @author zh_cr
 */
@Data
@NoArgsConstructor
public class AddCartReq {

    @NonNull
    // 商品ID
    private String skuId;

    @NonNull
    // 商品变化数量
    private Integer num;

    // 当前登录用户名
    private String username;
}
