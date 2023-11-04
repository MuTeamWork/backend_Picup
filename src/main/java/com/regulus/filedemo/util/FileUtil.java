package com.regulus.filedemo.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;



/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-11-01
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FileUtil {


    public  static  void setMetadata(Metadata metadata)  {

        Map<String, String> map = new HashMap<>();

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                log.info(tagName + "===" + desc);//照片信息
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    log.info("ERROR: "+ error);
                }
            }
        }
        log.info(map.toString());
        //=============提取全部数据====================
//        for (Directory directory : metadata.getDirectories()) {
//            for (Tag tag : directory.getTags()) {
//                String tagName = tag.getTagName();  //标签名
//                String desc = tag.getDescription(); //标签信息
//                log.info(tagName + "===" + desc);//照片信息
//            }
//        }
    }

    public static void eraseExif(String path, String extendName) throws IOException {
        File sourceImageFile = new File(path);
        // 读取图像
        BufferedImage image = ImageIO.read(sourceImageFile);

        // 将图像重新写入新文件夹
        File destinationFile = new File(path);
        ImageIO.write(image,extendName ,destinationFile);
    }

    public static String getFileType(String filePath) {
        FileInputStream fis = null;
        String extension = null;

        try {
            fis = new FileInputStream(filePath);
            byte[] magicBytes = new byte[8]; // 读取前8个字节来判断文件类型

            if (fis.read(magicBytes) >= 8) {
                extension = identifyFileType(magicBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.info("获取文件类型出错 : " + filePath);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return extension;
    }

    private static String identifyFileType(byte[] magicBytes) {
        // 判断文件类型的魔法字节
        if (isJPEG(magicBytes)) {
            return "JPEG";
        } else if (isPNG(magicBytes)) {
            return "PNG";
        } else if (isGIF(magicBytes)) {
            return "GIF";
        } else if(isBMP(magicBytes)){
            // 在这里可以添加更多的文件类型判断
            return "BMP";
        } else{
            return "Unknown";
        }
    }

    private static boolean isJPEG(byte[] magicBytes) {
        return (magicBytes[0] == (byte) 0xFF) && (magicBytes[1] == (byte) 0xD8);
    }

    private static boolean isPNG(byte[] magicBytes) {
        return (magicBytes[0] == (byte) 0x89) && (magicBytes[1] == (byte) 0x50)
                && (magicBytes[2] == (byte) 0x4E) && (magicBytes[3] == (byte) 0x47)
                && (magicBytes[4] == (byte) 0x0D) && (magicBytes[5] == (byte) 0x0A)
                && (magicBytes[6] == (byte) 0x1A) && (magicBytes[7] == (byte) 0x0A);
    }

    private static boolean isGIF(byte[] magicBytes) {
        return (magicBytes[0] == (byte) 'G') && (magicBytes[1] == (byte) 'I')
                && (magicBytes[2] == (byte) 'F') && (magicBytes[3] == (byte) '8')
                && ((magicBytes[4] == (byte) '7') || (magicBytes[4] == (byte) '9'))
                && (magicBytes[5] == (byte) 'a');
    }

    private static boolean isBMP(byte[] magicBytes) {
        return (magicBytes[0] == 0x42) && (magicBytes[1] == 0x4D);
    }

}
