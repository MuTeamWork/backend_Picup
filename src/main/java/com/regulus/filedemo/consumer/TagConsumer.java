package com.regulus.filedemo.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.regulus.filedemo.config.RabbitmqConfig;
import com.regulus.filedemo.entity.Tag;
import com.regulus.filedemo.entity.TagFile;
import com.regulus.filedemo.entity.User;
import com.regulus.filedemo.mapper.TagFileMapper;
import com.regulus.filedemo.mapper.TagMapper;
import com.regulus.filedemo.request.TagRequest;
import com.regulus.filedemo.service.RedisServiceImp;
import com.regulus.filedemo.util.SnowflakeExample;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class TagConsumer {

    @Resource
    RedisServiceImp redisServiceImp;

    @Resource
    TagMapper tagMapper;

    @Resource
    TagFileMapper tagFileMapper;

    @RabbitListener(queues = RabbitmqConfig.TAG_CONFIRM_QUEUE_NAME)
    private void getJson(String json) throws InterruptedException {

       JSONObject object = JSONUtil.parseObj(json);
        Long fid = (Long)object.get("fid");
        List<String> tags = (List<String>)object.get("tag");

        for(String tag : tags) {
            Long tidTemple = redisServiceImp.checkIfExistFromMap(tag);
            if( tidTemple == -1L){
                //返回-1，说明没有添加过，要往两个表里添加
                //往tag表里添加新的tag
                Tag tag0 = new Tag();
                Long OriginalId = SnowflakeExample.GenerateSnowFlake();
                Long realFid0 = OriginalId >>> 12;
                tag0.setTid(realFid0);
                tag0.setTag(tag);
                tagMapper.insert(tag0);
                //往tag_file表里添加对应关系
                TagFile tagFile = new TagFile();
                tagFile.setFid(fid);
                tagFile.setTid(tag0.getTid());
                tagFileMapper.insert(tagFile);
                redisServiceImp.addToMap(tag,tag0.getTid() + "");
            } else{
                //返回了tid，插入tag_file表
                TagFile tagFile = new TagFile();
                tagFile.setFid(fid);
                tagFile.setTid(tidTemple);
                tagFileMapper.insert(tagFile);
            }
        }

        log.info(json);
        return;
    }
}
