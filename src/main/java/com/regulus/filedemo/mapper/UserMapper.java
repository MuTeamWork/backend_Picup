package com.regulus.filedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.filedemo.entity.Setting;
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
 * @since 2023-10-30
 */

public interface UserMapper extends BaseMapper<User> {
    Setting querySettingById(@Param("uid") Long uid);
}
