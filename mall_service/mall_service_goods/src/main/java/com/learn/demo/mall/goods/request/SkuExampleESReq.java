package com.learn.demo.mall.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

/**
 * 商品搜索请求类
 * @author zh_cr
 */
@Data
@NoArgsConstructor
public class SkuExampleESReq {

    /**
     * 商品名称
     */
    @NonNull
    private String name;

    /**
     * 是否高亮搜索结果
     */
    private Boolean highlight;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 规格过滤
     */
    private Map<String, Object> specExample;

    /**
     * 最低价
     */
    private Integer minPrice;

    /**
     * 最高价
     */
    private Integer maxPrice;

    /**
     * 是否按品牌聚合
     */
    private Boolean aggOnBrandName;

    /**
     * 是否按规格聚合
     */
    private Boolean aggOnSpec;

    /**
     * 排序规则（相关性）：0-升序，1-降序
     * @see com.learn.demo.mall.goods.enums.SortOrderEnum
     */
    private Integer sortOrder;

    /**
     * 排序字段
     */
    private String sortKey;
}
