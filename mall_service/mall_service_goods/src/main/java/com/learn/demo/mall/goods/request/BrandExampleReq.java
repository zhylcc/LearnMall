package com.learn.demo.mall.goods.request;

import lombok.Data;

/**
 * 品牌条件查询请求参数
 *
 * @author zh_cr
 */
@Data
public class BrandExampleReq {

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌的首字母
     */
    private String letter;
}
