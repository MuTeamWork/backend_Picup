package com.regulus.filedemo.service;

import com.regulus.filedemo.entity.ImageInfo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 *
 */
@Service
public class FileService {

    @Value("${file-demo.domain}")
    private String domain;

    @Value("${file-demo.prefix-image}")
    private String prefixImage;

    @Value("${file-demo.prefix-thumbnail}")
    private String prefixThu;

    @Value("${file-demo.file-image-path}")
    private String fileImagePath;

    @Value("${file-demo.file-thumbnail-path}")
    private  String fileThumbnailPath;

    /**
     * 1.本地存储图片
     * 2.生成图片 url 信息
     * 3.返回给前端
     *
     * @param file
     * @return
     */
    public ImageInfo uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        //获取原始文件名，用 . 分隔开
        String[] split = originalFilename.split("\\.");

        //获取拓展名
        String extendName = split[split.length - 1];

        //UUID作为保存名称
        String imageName = UUID.randomUUID().toString() + "." + extendName;

        //UUID作为保存名称
        String thumbnailName = UUID.randomUUID().toString() + ".jpg";

        //保存到本地路径
        String pathName = fileImagePath + imageName;



        file.transferTo(new File(pathName));

        String a = fileImagePath + imageName;
        String b = fileThumbnailPath + thumbnailName;
        Thumbnails.of(a).scale(0.5f,0.1f)
                .outputFormat("jpg")
                .outputQuality(0.01f)
                .toFile(b);

        //原图
        String url0 = domain + prefixImage + imageName;

        //缩略图
        String url1 = domain + prefixThu + thumbnailName;


        return new ImageInfo(originalFilename,url0,url1);
    }
}
