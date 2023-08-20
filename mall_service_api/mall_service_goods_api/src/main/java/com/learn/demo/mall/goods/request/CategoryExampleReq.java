package com.learn.demo.mall.goods.request;

import lombok.Data;

/**
 * 分类条件查询请求参数
 *
 * @author zh_cr
 */
@Data
public class CategoryExampleReq {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 上级ID
     */
    private Integer parentId;

    /**
     * 模板ID
     */
    private Integer templateId;
}
