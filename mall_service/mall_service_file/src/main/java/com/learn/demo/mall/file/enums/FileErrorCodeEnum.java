package com.learn.demo.mall.file.enums;

import com.learn.demo.mall.common.enums.ErrorCode;

/**
 * @author zh_cr
 */

public enum FileErrorCodeEnum implements ErrorCode {

    ARGUMENT_ILLEGAL(20000, "文件服务参数不合法"),
    THIRD_SERVICE_CALL_EXCEPTION(20001, "文件服务中三方服务调用异常"),
    BIZ_FDFS_WARNING(20100, "fastdfs服务异常提示"),
    BIZ_FDFS_UPLOAD_ERROR(20101, "fastdfs上传文件出错"),
    BIZ_FDFS_DOWNLOAD_ERROR(20102, "fastdfs下载文件出错"),
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
