package com.learn.demo.mall.goods.exception;

import com.learn.demo.mall.common.response.StatusCode;
import com.learn.demo.mall.common.exception.MallException;

/**
 * 商品微服务异常类
 * @author zh_cr
 */
public class GoodsException extends MallException {
    public GoodsException(StatusCode errorCode) {
        super(errorCode);
    }

    public GoodsException(String message, StatusCode errorCode) {
        super(message, errorCode);
    }

    public GoodsException(Throwable cause, StatusCode errorCode) {
        super(cause, errorCode);
    }

    public GoodsException(String message, Throwable cause, StatusCode errorCode) {
        super(message, cause, errorCode);
    }
}
