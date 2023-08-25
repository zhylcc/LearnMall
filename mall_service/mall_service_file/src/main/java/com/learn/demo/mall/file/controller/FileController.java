package com.learn.demo.mall.file.controller;

import com.learn.demo.mall.common.enums.BasicErrorCodeEnum;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.file.service.FileService;
import com.learn.demo.mall.file.enums.FileErrorCodeEnum;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * FastDFS
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Resource
    private HttpServletResponse response;

    @Resource
    private FileService fileService;

    @PostMapping
    public Result<String> uploadFile(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new BaseBizException("文件不存在", FileErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        try {
            String fileUrl = fileService.uploadFile(file);
            return Result.success(fileUrl);
        } catch (Exception e) {
            throw new BaseBizException("文件上传出错", FileErrorCodeEnum.BIZ_FAST_DFS_UPLOAD_ERROR);
        }
    }

    @GetMapping
    public void downloadFile(@RequestParam String fileName) throws IOException {
        byte[] bytes;
        try {
            bytes = fileService.downloadFile(fileName);
        } catch (Exception e) {
            throw new BaseBizException("文件下载出错", FileErrorCodeEnum.BIZ_FAST_DFS_DOWNLOAD_ERROR);
        }
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (Exception e) {
            throw new BaseBizException("文件微服务未知异常", BasicErrorCodeEnum.UNKNOWN_ERROR);
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @DeleteMapping
    public Result<Void> deleteFile(@RequestParam String fileName) {
        try {
            fileService.deleteFile(fileName);
            return Result.success();
        } catch (Exception e) {
            throw new BaseBizException("文件删除出错", FileErrorCodeEnum.BIZ_FAST_DFS_DELETE_ERROR);
        }
    }
}
