package com.regulus.filedemo.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImp {

    @Resource
    private  RedisTemplate redisTemplate;
    // 添加键值对到Redis
    public void setValue(String value, Long time) {
        Long l = System.currentTimeMillis() + time;

        redisTemplate.opsForZSet().add("Key", value , l);
    }

    // 获取Redis中的值
    public Set<String> getValue() {
        return redisTemplate.opsForZSet().rangeByScore("Key",0,System.currentTimeMillis());
    }

    // 设置键的过期时间
    public void expireKey(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    // 删除键
    public Boolean deleteMember(String member) {
        return redisTemplate.opsForZSet().remove("Key",member) >= 1L;
    }
}
