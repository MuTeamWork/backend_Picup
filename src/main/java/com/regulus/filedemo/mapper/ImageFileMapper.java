package com.regulus.filedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.filedemo.entity.ImageFile;
import com.regulus.filedemo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-10-31
 */
public interface ImageFileMapper extends BaseMapper<ImageFile> {
    List<ImageFile> getFileList(@Param("uid") Long uid);
}
