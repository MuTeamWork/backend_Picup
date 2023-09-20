package com.regulus.filedemo.entity;

import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-09-20
 */
@NoArgsConstructor
public class ImageInfo implements Serializable {
    String imageName;
    String fileImagePath;
    String fileThumbnailPath;
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFileImagePath() {
        return fileImagePath;
    }

    public void setFileImagePath(String fileImagePath) {
        this.fileImagePath = fileImagePath;
    }

    public String getFileThumbnailPath() {
        return fileThumbnailPath;
    }

    public void setFileThumbnailPath(String fileThumbnailPath) {
        this.fileThumbnailPath = fileThumbnailPath;
    }

    public ImageInfo(String imageName, String fileImagePath, String fileThumbnailPath) {
        this.imageName = imageName;
        this.fileImagePath = fileImagePath;
        this.fileThumbnailPath = fileThumbnailPath;
    }


}
