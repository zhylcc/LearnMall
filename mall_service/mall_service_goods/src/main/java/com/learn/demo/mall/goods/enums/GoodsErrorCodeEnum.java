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
    BIZ_ES_IMPORT_SKU_ERROR(10401, "商品管理导入sku索引异常"),
    BIZ_ES_DELETE_SKU_ERROR(10402, "商品管理删除sku索引异常"),
    BIZ_SKU_LACK(10403, "商品Sku库存不足"),
    BIZ_FAST_DFS_WARNING(10500, "FastDFS异常提示"),
    BIZ_FAST_DFS_UPLOAD_ERROR(10501, "FastDFS上传文件出错"),
    BIZ_FAST_DFS_DOWNLOAD_ERROR(10502, "FastDFS下载文件出错"),
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
