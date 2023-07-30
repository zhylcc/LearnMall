package com.learn.demo.mall.goods.enums;

import com.learn.demo.mall.common.enums.ErrorCode;

/**
 * @author zh_cr
 */
public enum GoodsErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(10000, "商品服务参数不合法"),
    THIRD_SERVICE_CALL_EXCEPTION(10001, "商品服务中三方服务调用异常"),
    BIZ_BRAND_WARNING(10100, "品牌管理异常提示"),
    BIZ_CATEGORY_WARNING(10200, "分类管理异常提示"),
    BIZ_SPEC_WARNING(10300, "规格管理异常提示"),
    BIZ_SPU_WARNING(10400, "商品管理异常提示"),
    ;

    private final Integer code;

    private final String message;

    GoodsErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
