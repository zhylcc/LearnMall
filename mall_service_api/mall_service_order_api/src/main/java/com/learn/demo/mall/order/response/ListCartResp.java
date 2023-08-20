package com.learn.demo.mall.order.response;

import com.learn.demo.mall.order.pojo.OrderItemPO;
import lombok.Data;

import java.util.List;

/**
 * 购物车列表数据
 * @author zh_cr
 */
@Data
public class ListCartResp {

    /**
     * 订单条目
     */
    private List<OrderItemPO> items;

    /**
     * 商品数
     */
    private Integer total;

    /**
     * 总价
     */
    private Integer money;

}
