package com.regulus.filedemo.service;

import cn.hutool.core.util.BooleanUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImp {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    // 添加元素到集合
    public Long checkIfExistFromMap(String key) {
      Object o =  stringRedisTemplate.opsForHash().get("tag",key);
       if( o == null){
           return -1L;
       }else {
           return Long.parseLong((String) o);
       }

    }

    public boolean addToMap(String key, String value){
        return BooleanUtil.isTrue( stringRedisTemplate.opsForHash().putIfAbsent("tag",key,value));
    }




    // 添加键值对到Redis
    public void setValue(String value, Long time) {
        Long l = System.currentTimeMillis() + time;

        stringRedisTemplate.opsForZSet().add("Key", value , l);
    }

    // 获取Redis中的值
    public Set<String> getValue() {
        return stringRedisTemplate.opsForZSet().rangeByScore("Key",0,System.currentTimeMillis());
    }

    // 设置键的过期时间
    public void expireKey(String key, long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }

    // 删除键
    public Boolean deleteMember(String member) {
        return stringRedisTemplate.opsForZSet().remove("Key",member) >= 1L;
    }
}
