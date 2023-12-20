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
import com.regulus.filedemo.util.SnowflakeExample;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


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

    @Resource
    RedisService redisService;

    /**
     * 1.本地存储图片
     * 2.生成图片 url 信息
     * 3.返回给前端
     *
     * @param file
     * @return
     */
    public ImageFile uploadImage(MultipartFile file, Long uid) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //获取原始文件名，用 . 分隔开
        String[] split = originalFilename.split("\\.");

        //获取拓展名
        String extendName = split[split.length - 1];

        //UUID作为保存名称
        String imageName = UUID.randomUUID().toString() + "." + extendName;

        //UUID作为保存名称
        String thumbnailName = UUID.randomUUID().toString() + "." + extendName;

        //保存到本地路径
        String pathName = fileImagePath + imageName;

        try {
            Path directoryImagePath = Paths.get(fileImagePath);
            Path directoryThuPath = Paths.get(fileThumbnailPath);

            // 检查目录是否存在，不存在则创建目录
            if (!Files.exists(directoryImagePath)) {
                Files.createDirectories(directoryImagePath);
                log.info("目录创建成功：" + fileImagePath);
            }
            if (!Files.exists(directoryThuPath)) {
                Files.createDirectories(directoryImagePath);
                log.info("目录创建成功：" + fileThumbnailPath);
            }
            log.info("路径存在：1." + directoryImagePath.toString() + " " + "2." + fileImagePath);

            // 构建文件完整路径
            Path filePath = directoryImagePath.resolve(imageName);

            log.info("完整路径存在：1." + directoryImagePath.toString());

            // 假设 fileBytes 是您从其他地方获取的文件内容的字节数组
            byte[] fileBytes = file.getBytes();

            // 将文件保存到服务器文件夹
            Files.write(filePath, fileBytes);
            log.info("文件保存成功！");
        } catch (IOException e) {
            throw e;
        }


        Setting setting = userMapper.querySettingById(uid);
        if(setting.getExif() == 0)
        FileUtil.eraseExif(pathName,extendName);


        String a = fileImagePath + imageName;
        String b = fileThumbnailPath + thumbnailName;
        log.info("原图路径：" + a);
        log.info("缩略图路径： " + b);

        if(!extendName.equals("gif")) {
            Thumbnails.of(a).scale(1f, 1f)
                    .outputFormat(extendName)
                    .outputQuality(0.1f)
                    .toFile(b);
        }
        else{

            Thumbnails.of(a).scale(0.8f, 0.8f)
                    .outputQuality(0.1)
                    .outputFormat(extendName)
                    .toFile(b);
        }

        log.info("缩略图生成成功");

        //原图
        String url0 = domain + prefixImage + imageName;

        //缩略图
        String url1 = domain + prefixThu + thumbnailName;


        ImageFile file1 = new ImageFile();
        file1.setFile(url0);
        file1.setThumbnail(url1);
        file1.setFileName(originalFilename);

        Long OriginalId = SnowflakeExample.GenerateSnowFlake();
        Long realFid0 = OriginalId >>> 12;
        Long fid = realFid0;
        file1.setFid(fid);


        Long timeInMillis = 0L;
        if(setting.getExpireTime() == 0){
            timeInMillis = System.currentTimeMillis() + 3155760000000L; // 假设这是代表毫秒的时间戳
        } else {
            setting.setExpireTime(setting.getExpireTime() * 1000);
            timeInMillis = System.currentTimeMillis() + setting.getExpireTime(); // 假设这是代表毫秒的时间戳
        }




        Timestamp timestamp = new Timestamp(timeInMillis);

        // 定义自定义的日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(timestamp);

        if(setting.getExpireTime() != 0)
        file1.setExpireTime(formattedDate);
        else
            file1.setExpireTime("unlimited");


        imageFileMapper.insert(file1);
        UserFile userFile = new UserFile();
        userFile.setFid(file1.getFid());
        userFile.setUid(uid);

        userFileMapper.insert(userFile);

        String value = fid + "." + uid;

        Integer expireTime = 1000;

        if(setting.getExpireTime() != 0) {
            expireTime = setting.getExpireTime();
            redisService.setValue(value, (long)expireTime);
        }



        return file1;
    }

    public ImageFile uploadWithoutLogin(MultipartFile file) throws IOException{

        String originalFilename = file.getOriginalFilename();
        //获取原始文件名，用 . 分隔开
        String[] split = originalFilename.split("\\.");

        //获取拓展名
        String extendName = split[split.length - 1];

        //UUID作为保存名称
        String imageName = UUID.randomUUID().toString() + "." + extendName;

        //UUID作为保存名称
        String thumbnailName = UUID.randomUUID().toString() + "." + extendName;

        //保存到本地路径
        String pathName = fileImagePath + imageName;

        try {
            Path directoryImagePath = Paths.get(fileImagePath);
            Path directoryThuPath = Paths.get(fileThumbnailPath);

            // 检查目录是否存在，不存在则创建目录
            if (!Files.exists(directoryImagePath)) {
                Files.createDirectories(directoryImagePath);
                log.info("目录创建成功：" + fileImagePath);
            }
            if (!Files.exists(directoryThuPath)) {
                Files.createDirectories(directoryImagePath);
                log.info("目录创建成功：" + fileThumbnailPath);
            }
            log.info("路径存在：1." + directoryImagePath.toString() + " " + "2." + fileImagePath);

            // 构建文件完整路径
            Path filePath = directoryImagePath.resolve(imageName);

            log.info("完整路径存在：1." + directoryImagePath.toString());

            // 假设 fileBytes 是您从其他地方获取的文件内容的字节数组
            byte[] fileBytes = file.getBytes();

            // 将文件保存到服务器文件夹
            Files.write(filePath, fileBytes);
            log.info("文件保存成功！");
        } catch (IOException e) {
            throw e;
        }

        FileUtil.eraseExif(pathName,extendName);
        Long timeInMillis = System.currentTimeMillis() + 3155760000000L; // 假设这是代表毫秒的时间戳

        Timestamp timestamp = new Timestamp(timeInMillis);

        // 定义自定义的日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(timestamp);

        //原图
        String url0 = domain + prefixImage + imageName;

        ImageFile file1 = new ImageFile();
        file1.setFile(url0);
        file1.setFileName(originalFilename);
        file1.setExpireTime(formattedDate);
        file1.setThumbnail("null");
        Long OriginalId = SnowflakeExample.GenerateSnowFlake();
        Long realFid0 = OriginalId >>> 12;
        Long fid = realFid0;
        file1.setFid(fid);


        imageFileMapper.insert(file1);
        return file1;
    }

    public void deleteFile(List<Long> fids, Long uid) throws IOException {
            for (Long fid : fids) {
                QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
                wrapper.eq("uid", uid);
                wrapper.eq("fid", fid);
                UserFile userFile = userFileMapper.selectOne(wrapper);

                if (userFile == null) {
                    //throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
                } else {
                    ImageFile file = imageFileMapper.selectById(fid + "");
                    if (file == null) {
                        //throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
                    }

                    String fileUUID0 = file.getThumbnail();
                    String[] split0 = fileUUID0.split("/");

                    String fileUUID1 = file.getFile();
                    String[] split1 = fileUUID1.split("/");

                    String filePath0 = fileThumbnailPath + split0[split0.length - 1];
                    String filePath1 = fileImagePath + split1[split1.length - 1];
                    try {
                        File deFile0 = new File(filePath0);
                        File deFile1 = new File(filePath1);
                        deFile0.delete();
                        deFile1.delete();
                    } catch (RuntimeException e) {
                        log.error("file doesn't exist, and file in table deleted");
                    }

                    userFileMapper.delete(wrapper);
                    imageFileMapper.deleteById(fid);
                    log.info("file successfully deleted: " + file);
                }
            }
        }

//        for (int i = 0; i < fids.length; i++) {
//
//            Long fid = fids[i];
//
//            QueryWrapper<UserFile> wrapper = new QueryWrapper<>();
//            wrapper.eq("uid",uid);
//            wrapper.eq("fid",fid);
//            UserFile userFile =  userFileMapper.selectOne(wrapper);
//            if(userFile == null){
//                throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
//            } else{
//                ImageFile file = imageFileMapper.selectById(fid + "");
//                if(file == null) throw new AppException(AppExceptionCodeMsg.FILE_NOT_EXIST);
//
//                String fileUUID0 = file.getThumbnail();
//                String[] split0 = fileUUID0.split("/");
//
//                String fileUUID1 = file.getFile();
//                String[] split1 = fileUUID1.split("/");
//
//                String filePath0 = "file/thumbnail/" + split0[split0.length -1];
//                String filePath1 = "file/image/" + split1[split1.length -1];
//                try{
//                File deFile0 = new File( filePath0);
//                File deFile1 = new File(filePath1);
//                deFile0.delete();
//                deFile1.delete();
//                } catch (RuntimeException e) {
//                    log.error("file doesn't exist,and file in table deleted");
//                }
//
//
//
//                userFileMapper.delete(wrapper);
//                imageFileMapper.deleteById(fid);
//                log.info("file successfully deleted: " + file);
//            }
//        }


    public List<ImageFile> getFileList(Long uid){
        return imageFileMapper.getFileList(uid);
    }


}
