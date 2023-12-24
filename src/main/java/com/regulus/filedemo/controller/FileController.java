package com.regulus.filedemo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;

import com.drew.imaging.ImageProcessingException;
import com.regulus.filedemo.config.RabbitmqConfig;
import com.regulus.filedemo.entity.ImageFile;
import com.regulus.filedemo.entity.Tag;
import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;
import com.regulus.filedemo.request.ImageFileRequest;
import com.regulus.filedemo.request.TagRequest;
import com.regulus.filedemo.response.Resp;
import com.regulus.filedemo.service.ImageFileServiceImp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;



@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private ImageFileServiceImp fileService;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @PostMapping("/fileUpload")
    public Resp<ImageFile> upload(MultipartFile file) throws IOException, ImageProcessingException {
        //file校验
        if(file.isEmpty()){
            throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
        }

        if(StpUtil.isLogin()) {
            ImageFile imageFile = fileService.uploadImage(file,
                    Long.parseLong((String) StpUtil.getLoginId()));

            //rabbitmq 生产者
            log.info("当前时间：{}，发送高级发布确认消息：{}", new Date(), imageFile);
            rabbitTemplate.convertAndSend(RabbitmqConfig.CONFIRM_EXCHANGE,
                    RabbitmqConfig.PYTHON_ROUTING_KEY , JSONUtil.toJsonStr(imageFile));


            return Resp.success(imageFile);
        }
        ImageFile imageFile = fileService.uploadWithoutLogin(file);




        return Resp.success(imageFile);

    }

    @PostMapping("/fileDelete")
    public Resp deleteFile(@RequestBody ImageFileRequest imageFileRequest) throws IOException {

        fileService.deleteFile(imageFileRequest.getFids(),Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success();
    }

    //
    @GetMapping("/getFileList")
    public Resp<List<ImageFile>> getFileList() {

        List<ImageFile> files = fileService.getFileList(Long.parseLong((String)StpUtil.getLoginId()));


            for (ImageFile file : files) {

                if(file == null) break;

                file.setTags(fileService.getFileTagList(file.getFid()));
            }


        return Resp.success(files);
    }

    @PostMapping("/getFileListByTags")
    public Resp<List<ImageFile>> getFileListByTags(@RequestBody TagRequest tagRequest) {
        List<ImageFile> files = fileService.selectFidByUidAndTids(Long.parseLong((String)StpUtil.getLoginId()), tagRequest.getTags());
        for (ImageFile file : files) {
            file.setTags(fileService.getFileTagList(file.getFid()));
        }
        return Resp.success(files);
    }

    @GetMapping("/getFileTagListByUid")
    public Resp<List<Tag>> getFileTagListByUid(){
        List<Tag> tags = fileService.getFileTagListByUid(Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success(tags);
    }



}
