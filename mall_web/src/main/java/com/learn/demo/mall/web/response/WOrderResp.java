package com.learn.demo.mall.web.response;

import com.learn.demo.mall.user.pojo.AddressPO;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import lombok.Data;

import java.util.List;

/**
 * @author zh_cr
 */
@Data
public class WOrderResp {

    /**
     * 收货地址列表
     */
    private List<AddressPO> address;

    /**
     * 商品sku清单
     */
    private List<OrderItemPO> cart;

    /**
     * 商品sku数
     */
    private Integer total;

    /**
     * 总金额
     */
    private Integer money;
}
