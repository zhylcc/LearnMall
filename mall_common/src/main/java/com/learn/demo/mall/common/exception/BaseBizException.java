package com.learn.demo.mall.common.exception;

/**
 * 统一异常类
 * @author zh_cr
 */
public class BaseBizException extends RuntimeException{

    private final ErrorCode errorCode;

    public BaseBizException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
