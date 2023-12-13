package com.regulus.filedemo;

import cn.dev33.satoken.SaManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.regulus.filedemo.mapper")
@EnableScheduling
@SpringBootApplication
@EnableCaching // 开启缓存支持
public class FileDemoApplication {

    public static void main(String[] args) throws JsonProcessingException{
        SpringApplication.run(FileDemoApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());

    }

}
