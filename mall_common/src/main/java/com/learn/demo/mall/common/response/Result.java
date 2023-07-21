package com.learn.demo.mall.common.response;

import com.learn.demo.mall.common.enums.StatusCodeEnum;
import lombok.*;

/**
 * 响应结果
 * @author zh_cr
 */
@Data
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
        return new Result<>(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(StatusCodeEnum.UNKNOWN_ERROR.getCode(), StatusCodeEnum.UNKNOWN_ERROR.getMessage());
    }

    public static <T> Result<T> fail(Integer errorCode, String message) {
        return new Result<>(errorCode, message);
    }
}
