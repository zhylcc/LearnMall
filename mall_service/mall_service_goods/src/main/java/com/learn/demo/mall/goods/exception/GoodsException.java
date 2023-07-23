package com.learn.demo.mall.goods.exception;

import com.learn.demo.mall.common.enums.ErrorCode;

/**
 * @author zh_cr
 */
public class GoodsException extends RuntimeException{

    private final ErrorCode errorCode;

    public GoodsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
