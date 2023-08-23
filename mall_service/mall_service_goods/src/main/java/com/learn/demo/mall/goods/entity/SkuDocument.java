package com.learn.demo.mall.goods.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Map;

/**
 * Sku文档实体
 *
 * @author zh_cr
 */
@Getter
@Setter
@Document(indexName = "sku", type = "docs")
public class SkuDocument implements Serializable {

    /**
     * 商品id
     */
    @Id
    private String id;

    /**
     * SKU名称
     */
    @Field(store = true, type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    /**
     * 价格（分）
     */
    @Field(store = true, type = FieldType.Integer)
    private Integer price;

    /**
     * 品牌名称
     */
    @Field(store = true, type = FieldType.Keyword)
    private String brandName;

    /**
     * 规格
     */
    private String spec;

    /**
     * 规格参数
     */
    private Map<String, Object> specMap;
}
