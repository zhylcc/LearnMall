package com.learn.demo.mall.goods.enums;

/**
 * @author zh_cr
 */

public enum SpuStatusEnum {

    NOT_DELETE("0", "未删除"),
    DELETE("1", "已删除"),
    NOT_MARKETABLE("0", "未上架"),
    MARKETABLE("1", "已上架"),
    NOT_CHECKED("0", "未审核"),
    CHECKED("1", "已审核"),
    ;

    private final String value;

    SpuStatusEnum(String value, String message) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
