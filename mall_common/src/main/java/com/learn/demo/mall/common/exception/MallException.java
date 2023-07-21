package com.learn.demo.mall.common.exception;

import com.learn.demo.mall.common.response.StatusCode;

/**
 * @author zh_cr
 */
public class MallException extends RuntimeException{

    private final StatusCode errorCode;

    public MallException(StatusCode errorCode) {
        super("[" + errorCode.getCode() + "]" + errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MallException(String message, StatusCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MallException(Throwable cause, StatusCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public MallException(String message, Throwable cause, StatusCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public StatusCode getErrorCode() {
        return errorCode;
    }

}
