package com.learn.demo.mall.goods.exception;

import com.learn.demo.mall.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品微服务统一异常处理类
 * @author zh_cr
 */
@ControllerAdvice
@Slf4j
public class GoodsExceptionHandler {

    @ExceptionHandler(GoodsException.class)
    @ResponseBody
    public Result<Object> error(GoodsException e) {
        log.warn(e.getMessage());
        return Result.fail(e.getErrorCode().getCode(), e.getMessage());
    }
}
