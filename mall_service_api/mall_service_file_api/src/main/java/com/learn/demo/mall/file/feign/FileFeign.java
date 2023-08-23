package com.learn.demo.mall.file.feign;

import com.learn.demo.mall.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zh_cr
 */
@FeignClient(name = "com/learn/demo/mall/file")
public interface FileFeign {

    @PostMapping("/file")
    Result<String> uploadFile(MultipartFile file);

    @GetMapping("/file")
    Result<Void> downloadFile(@RequestParam String fileName, Object response) throws IOException;

    @DeleteMapping("/file")
    Result<Void> deleteFile(@RequestParam String fileName);
}
