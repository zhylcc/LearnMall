package com.learn.demo.mall.goods.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.util.FastDFSUtil;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
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
@RequestMapping("/goods/file")
public class FileController {

    @Autowired
    private FastDFSUtil fastDfsUtil;

    @PostMapping
    public Result<String> uploadFile(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new BaseBizException("文件不存在", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        try {
            String fileUrl = fastDfsUtil.uploadFile(file);
            return Result.success(fileUrl);
        } catch (Exception e) {
            throw new BaseBizException("文件上传出错", GoodsErrorCodeEnum.BIZ_FAST_DFS_UPLOAD_ERROR);
        }
    }

    @GetMapping
    public void downloadFile(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        byte[] bytes;
        try {
            bytes = fastDfsUtil.downloadFile(fileName);
        } catch (Exception e) {
            throw new BaseBizException("文件下载出错", GoodsErrorCodeEnum.BIZ_FAST_DFS_DOWNLOAD_ERROR);
        }
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (Exception e) {
            throw new BaseBizException("未知异常", GoodsErrorCodeEnum.BIZ_FAST_DFS_WARNING);
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @DeleteMapping
    private Result<Void> deleteFile(@RequestParam String fileName) {
        try {
            fastDfsUtil.deleteFile(fileName);
            return Result.success();
        } catch (Exception e) {
            throw new BaseBizException("未知异常", GoodsErrorCodeEnum.BIZ_FAST_DFS_WARNING);
        }
    }
}
