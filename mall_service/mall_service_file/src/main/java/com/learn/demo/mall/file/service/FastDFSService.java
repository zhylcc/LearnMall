package com.learn.demo.mall.file.service;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.file.enums.FileErrorCodeEnum;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * FastDFS工具类
 * @author zh_cr
 */
@Service
public class FastDFSService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream (file);
        StorePath storePath = storageClient.uploadFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension 文件后缀
     * @return 文件访问地址
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension,null);
        return getResAccessUrl(storePath);
    }

    /**
     * 删除文件
     * @param fileName 文件名（含后缀，group/xx/xx/xxx.x）
     */
    public void deleteFile(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(getResAccessUrl(fileName));
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            throw new BaseBizException("FastDFS文件名不存在", FileErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名（含后缀，group/xx/xx/xxx.x）
     * @return 文件字节
     */
    public byte[] downloadFile(String fileName) throws IOException {
        String group = fileName.substring(0, fileName.indexOf("/"));
        String path = fileName.substring(fileName.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        return storageClient.downloadFile(group, path, downloadByteArray);
    }

    /**
     * 封装完整URL地址
     */
    private String getResAccessUrl(StorePath storePath) {
        return fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
    }

    /**
     * 封装完整URL地址
     */
    private String getResAccessUrl(String fileName) {
        return fdfsWebServer.getWebServerUrl() + fileName;
    }
}
