package com.learn.demo.mall.file.advice;

import com.learn.demo.mall.common.enums.BasicErrorCodeEnum;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 业务异常处理类
 * @author zh_cr
 */
@RestControllerAdvice
@Slf4j
public class BizExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result<Void> handle(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(BasicErrorCodeEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(BaseBizException.class)
    public Result<Void> handleBiz(BaseBizException e) {
        log.warn(e.getMessage(), e);
        return Result.fail(e.getErrorCode().getCode(), e.getMessage());
    }
}
