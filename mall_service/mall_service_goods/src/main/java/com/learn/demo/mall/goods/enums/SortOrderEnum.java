package com.learn.demo.mall.goods.enums;


/**
 * 排序顺序枚举
 * @author zh_cr
 */
public enum SortOrderEnum {
    ASC(0, "升序"),
    DESC(1, "降序");

    private final Integer order;

    SortOrderEnum(Integer order, String message) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
}
