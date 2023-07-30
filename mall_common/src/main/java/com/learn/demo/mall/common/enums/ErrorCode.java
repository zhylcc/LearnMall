package com.learn.demo.mall.common.enums;

/**
 * 异常状态码
 * -1 - 系统异常
 * 第一位：所属微服务
 * 1 - 商品微服务
 * 2 - 文件微服务
 * 第二三位：所属功能模块，00表示微服务内通用
 * 第四五位：自定义
 *
 * @author zh_cr
 */
public interface ErrorCode {

    Integer getCode();

    String getMessage();
}
