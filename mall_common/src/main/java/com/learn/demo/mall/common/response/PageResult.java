package com.learn.demo.mall.common.response;

import lombok.*;

import java.util.List;

/**
 * 分页响应结果
 * @author zh_cr
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
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


    public static <T> PageResult<T> success(Long total, List<T> items) {
        return new PageResult<>(0, "成功", new PageList<>(total, items));
    }

}
