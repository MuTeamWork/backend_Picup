package com.regulus.filedemo;

import com.regulus.filedemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@MapperScan("com.regulus.filedemo.mapper")
@SpringBootTest
class FileDemoApplicationTests {



    @Test
    void contextLoads() {
        log.info("qq");
    }

}
