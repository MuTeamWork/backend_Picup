package com.regulus.filedemo.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnowflakeExample {
    public static Long GenerateSnowFlake() {
        // 使用Hutool生成Snowflake对象
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        // 生成雪花算法ID
        Long id = snowflake.nextId();

        return id;
    }
}
