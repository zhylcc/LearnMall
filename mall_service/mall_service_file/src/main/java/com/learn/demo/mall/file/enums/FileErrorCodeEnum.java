package com.learn.demo.mall.file.enums;

import com.learn.demo.mall.common.exception.ErrorCode;

/**
 * @author zh_cr
 */
public enum FileErrorCodeEnum implements ErrorCode {
    ARGUMENT_ILLEGAL(90000, "文件服务参数不合法"),
    THIRD_SERVICE_CALL_EXCEPTION(90001, "文件服务中三方服务调用异常"),
    BIZ_FAST_DFS_UPLOAD_ERROR(90002, "FastDFS上传文件出错"),
    BIZ_FAST_DFS_DOWNLOAD_ERROR(90003, "FastDFS下载文件出错"),
    BIZ_FAST_DFS_DELETE_ERROR(90004, "FastDFS删除文件出错"),
    ;

    private final Integer code;

    private final String message;

    FileErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
