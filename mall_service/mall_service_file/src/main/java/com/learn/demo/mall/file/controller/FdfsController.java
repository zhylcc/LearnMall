package com.learn.demo.mall.file.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.file.enums.FileErrorCodeEnum;
import com.learn.demo.mall.file.service.FastDFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/file/fdfs")
public class FdfsController {

    @Autowired
    private FastDFSService fastDfsService;

    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new BaseBizException("文件不存在", FileErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        try {
            String fileUrl = fastDfsService.uploadFile(file);
            return Result.success(fileUrl);
        } catch (IOException e) {
            throw new BaseBizException("文件上传出错", FileErrorCodeEnum.BIZ_FDFS_UPLOAD_ERROR);
        }
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        byte[] bytes;
        try {
            bytes = fastDfsService.downloadFile(fileName);
        } catch (IOException e) {
            throw new BaseBizException("文件下载出错", FileErrorCodeEnum.BIZ_FDFS_DOWNLOAD_ERROR);
        }
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new BaseBizException("文件已获取但无法传输", FileErrorCodeEnum.BIZ_FDFS_WARNING);
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @DeleteMapping("/delete")
    private Result<Void> deleteFile(@RequestParam String fileName) {
        fastDfsService.deleteFile(fileName);
        return Result.success();
    }

}
