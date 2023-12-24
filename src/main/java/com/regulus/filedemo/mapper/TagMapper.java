package com.regulus.filedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.filedemo.entity.ImageFile;
import com.regulus.filedemo.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> getFileTagListByUid(@Param("uid")Long uid);

    List<Tag> getFileTagList(@Param("fid") Long fid);

    List<ImageFile> selectFidByUidAndTags(@Param("uid") Long uid, @Param("tags") List<String> tags);
}
