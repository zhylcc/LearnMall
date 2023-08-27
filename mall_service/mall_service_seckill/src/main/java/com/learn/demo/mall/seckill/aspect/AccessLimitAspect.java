package com.learn.demo.mall.seckill.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.seckill.enums.SeckillErrorCodeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 自定义限流切面
 *
 * @author zh_cr
 */
@Component
@Scope
@Aspect
public class AccessLimitAspect {

    @Resource
    private HttpServletResponse response;

    /**
     * 每秒2个的令牌生成速率
     */
    private final RateLimiter rateLimiter = RateLimiter.create(2.0);


    @Pointcut("@annotation(com.learn.demo.mall.seckill.aspect.AccessLimit)")
    public void accessLimit() {}

    @Around("accessLimit()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        if (rateLimiter.tryAcquire()) {
            // 允许访问
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            // 限流
            String responseStr = JSON.toJSONString(Result.fail(SeckillErrorCodeEnum.RATE_LIMITER_BLOCKED.getCode(), "被限流无法访问"));
            ServletOutputStream outputStream = null;
            try {
                response.setContentType("application/json;charset=utf-8");
                outputStream = response.getOutputStream();
                outputStream.write(responseStr.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (Objects.nonNull(outputStream)) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
