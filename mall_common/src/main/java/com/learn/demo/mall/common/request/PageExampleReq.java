package com.learn.demo.mall.common.request;

import lombok.*;

/**
 * 分页查询请求参数封装
 * @author zh_cr
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PageExampleReq<T> {

    private T example;

    @NonNull
    private Integer currentPage;

    @NonNull
    private Integer pageSize;

}
