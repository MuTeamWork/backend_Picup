package com.regulus.filedemo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;

import com.drew.imaging.ImageProcessingException;
import com.regulus.filedemo.entity.ImageFile;
import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;
import com.regulus.filedemo.request.ImageFileRequest;
import com.regulus.filedemo.response.Resp;
import com.regulus.filedemo.service.ImageFileServiceImp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
@RequestMapping("/file")
public class FileController {

    @Resource
    private ImageFileServiceImp fileService;


    @PostMapping("/fileUpload")
    public Resp<ImageFile> upload(MultipartFile file) throws IOException, ImageProcessingException {
        //file校验
        if(file.isEmpty()){
            throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
        }
        ImageFile imageFile = fileService.uploadImage(file,
                Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success(imageFile);
    }

    @PostMapping("/fileDelete")
    public Resp deleteFile(@RequestBody ImageFileRequest imageFileRequest) throws IOException {

        fileService.deleteFile(imageFileRequest.getFids(),Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success();
    }

    @GetMapping("/getFileList")
    public Resp<List<ImageFile>> getFileList() {
        List<ImageFile> files = fileService.getFileList(Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success(files);
    }



}
