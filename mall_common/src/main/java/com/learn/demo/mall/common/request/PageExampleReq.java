package com.learn.demo.mall.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * 分页查询请求参数封装
 * @author zh_cr
 */
@Data
@AllArgsConstructor
public class PageExampleReq<T> {

    private T example;

    @NonNull
    private Integer currentPage;

    @NonNull
    private Integer pageSize;

}
