package com.learn.demo.mall.goods.exception;

import com.learn.demo.mall.common.enums.ErrorCode;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * 商品微服务统一异常处理类
 * @author zh_cr
 */
@RestControllerAdvice
@Slf4j
public class GoodsExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Object> handle(Exception e) {
        log.warn(e.getMessage(), e);
        ErrorCode errorCode = GoodsErrorCodeEnum.UNKNOWN_ERROR;
        return Result.fail(errorCode.getCode(), errorCode.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, HttpMediaTypeNotSupportedException.class})
    public Result<Object> handleMatch(Exception e) {
        log.info(e.getMessage(), e);
        ErrorCode errorCode = GoodsErrorCodeEnum.ARGUMENT_MISMATCH;
        return Result.fail(errorCode.getCode(), errorCode.getMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public Result<Object> handleSql(Exception e) {
        log.warn(e.getMessage(), e);
        ErrorCode errorCode = GoodsErrorCodeEnum.SQL_EXCEPTION;
        return Result.fail(errorCode.getCode(), errorCode.getMessage());
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public Result<Object> handleDb(Exception e) {
        log.warn(e.getMessage(), e);
        ErrorCode errorCode = GoodsErrorCodeEnum.DATABASE_ERROR;
        return Result.fail(errorCode.getCode(), errorCode.getMessage());
    }

    @ExceptionHandler(GoodsException.class)
    public Result<Object> handleBiz(GoodsException e) {
        log.info(e.getMessage(), e);
        return Result.fail(e.getErrorCode().getCode(), e.getMessage());
    }
}
