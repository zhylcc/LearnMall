package com.learn.demo.mall.seckill.aspect;

import java.lang.annotation.*;

/**
 * 自定义限流注解
 *
 * @author zh_cr
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
}
