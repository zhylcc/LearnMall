package com.learn.demo.mall.common.response;

import lombok.*;

/**
 * 响应结果
 * @author zh_cr
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    @NonNull
    private Integer code;

    @NonNull
    private String message;

    private T data;

    public static <T> Result<T> success() {
        return new Result<>(0, "成功");
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "成功", data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }
}
