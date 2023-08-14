package com.learn.demo.mall.monitor.enums;

/**
 * 商品上架状态
 * @author zh_cr
 */
public enum SpuMarketableRoutingKeyEnum {
    DOWN(0, "down", "下架"),
    UP(1, "up", "上架"),
    ;

    private final Integer status;
    private final String key;

    SpuMarketableRoutingKeyEnum(Integer status, String key, String message) {
        this.status = status;
        this.key = key;
    }

    public Integer getStatus() {
        return status;
    }

    public String getKey() {
        return key;
    }
}
