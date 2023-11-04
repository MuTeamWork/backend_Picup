package com.regulus.filedemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drew.imaging.ImageProcessingException;
import com.regulus.filedemo.entity.ImageFile;

import com.regulus.filedemo.entity.Setting;
import com.regulus.filedemo.entity.UserFile;
import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;
import com.regulus.filedemo.mapper.ImageFileMapper;
import com.regulus.filedemo.mapper.UserFileMapper;
import com.regulus.filedemo.mapper.UserMapper;
import com.regulus.filedemo.request.ImageFileRequest;

import com.regulus.filedemo.util.FileUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 *
 */

@Service
@Slf4j
public class ImageFileServiceImp {

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

    @Resource
    private ImageFileMapper imageFileMapper;

    @Resource
    private UserFileMapper userFileMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 1.本地存储图片
     * 2.生成图片 url 信息
     * 3.返回给前端
     *
     * @param file
     * @return
     */
    public ImageFile uploadImage(MultipartFile file, Long uid) throws IOException, ImageProcessingException {
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

//        Metadata metadata0 = ImageMetadataReader.readMetadata(file10);
//
//        FileUtil.setMetadata(metadata0);



        Setting setting = userMapper.querySettingById(uid);
        if(setting.getExif() == 0)
        FileUtil.eraseExif(pathName,extendName);
//        file10 = new File(pathName);

//        Metadata metadata1 = ImageMetadataReader.readMetadata(file10);
//
//        FileUtil.setMetadata(metadata1);

        String a = fileImagePath + imageName;
        String b = fileThumbnailPath + thumbnailName;
        Thumbnails.of(a).scale(0.1f,0.1f)
                .outputFormat("jpg")
                .outputQuality(0.2f)
                .toFile(b);

        //原图
        String url0 = domain + prefixImage + imageName;

        //缩略图
        String url1 = domain + prefixThu + thumbnailName;


        ImageFile file1 = new ImageFile();
        file1.setFile(url0);
        file1.setThumbnail(url1);
        file1.setFileName(originalFilename);
        //file1.setFid(IdUtil.getSnowflake(1, 1).nextId());

        imageFileMapper.insert(file1);
        UserFile userFile = new UserFile();
        userFile.setFid(file1.getFid());
        userFile.setUid(uid);
        userFileMapper.insert(userFile);

        return file1;
    }

    public void deleteFile(Long[] fids, Long uid) throws IOException {

        for (int i = 0; i < fids.length; i++) {

            Long fid = fids[i];

            QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
            wrapper.eq("uid",uid);
            wrapper.eq("fid",fid);
            UserFile userFile =  userFileMapper.selectOne(wrapper);
            if(userFile == null){
                throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
            } else{
                ImageFile file = imageFileMapper.selectById(fid + "");
                if(file == null) throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);

                String fileUUID0 = file.getThumbnail();
                String[] split0 = fileUUID0.split("/");

                String fileUUID1 = file.getFile();
                String[] split1 = fileUUID1.split("/");

                String filePath0 = "file/thumbnail/" + split0[split0.length -1];
                String filePath1 = "file/image/" + split1[split1.length -1];
                try{
                File deFile0 = new File( filePath0);
                File deFile1 = new File(filePath1);
                deFile0.delete();
                deFile1.delete();
                } catch (RuntimeException e) {
                    log.error("file doesn't exist,and file in table deleted");
                }



                userFileMapper.delete(wrapper);
                imageFileMapper.deleteById(fid);
                log.info("file successfully deleted: " + file);
            }
        }
    }

    public List<ImageFile> getFileList(Long uid){
        return imageFileMapper.getFileList(uid);
    }


}
