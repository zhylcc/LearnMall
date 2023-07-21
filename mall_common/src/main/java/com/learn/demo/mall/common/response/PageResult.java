package com.learn.demo.mall.common.response;

import lombok.*;

/**
 * 分页响应结果
 * @author zh_cr
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 状态码
     */
    @NonNull
    private Integer code;

    /**
     * 消息
     */
    @NonNull
    private String message;

    /**
     * 分页数据
     */
    private PageList<T> data;
}
