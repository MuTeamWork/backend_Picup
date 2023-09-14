package com.regulus.filedemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-09-14
 */

@Service
public class FileService {

    @Value("${file-demo.domain}")
    private String domain;

    @Value("${file-demo.prefix}")
    private String prefix;

    @Value("${file-demo.file-path}")
    private String filePath;

    /**
     * 1.本地存储图片
     * 2.生成图片 url 信息
     * 3.返回给前端
     *
     * @param file
     * @return
     */
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        //获取原始文件名，用 . 分隔开
        String[] split = originalFilename.split("\\.");

        //获取拓展名
        String extendName = split[split.length - 1];

        //UUID作为保存名称
        String fileName = UUID.randomUUID().toString() + "." + extendName;

        //保存到本地路径
        String pathName = filePath + fileName;

        //返回给前端的url
        String url = domain + prefix + fileName;

        file.transferTo(new File(pathName));
        return url;
    }
}
