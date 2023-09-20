package com.regulus.filedemo.controller;

import cn.hutool.json.JSONUtil;
import com.regulus.filedemo.entity.ImageInfo;
import com.regulus.filedemo.response.Resp;
import com.regulus.filedemo.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-09-14
 */

@Slf4j
@RestController
public class FileController {

    @Resource
    private FileService fileService;


    @PostMapping("/fileUpload")
    public String upload(MultipartFile file) throws IOException {
        ImageInfo i = fileService.uploadImage(file);
        return JSONUtil.toJsonStr(Resp.success(i));
    }



}
