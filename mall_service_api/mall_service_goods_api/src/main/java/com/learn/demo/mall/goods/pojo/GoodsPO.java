package com.learn.demo.mall.goods.pojo;

import lombok.Data;

import java.util.List;

/**
 * 商品实体
 * @author zh_cr
 */
@Data
public class GoodsPO {

    /**
     * spu
     */
    private SpuPO spu;

    /**
     * sku列表
     */
    private List<SkuPO> skuList;
}
