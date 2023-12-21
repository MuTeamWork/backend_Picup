package com.regulus.filedemo.scheduledtask;

import com.regulus.filedemo.service.ImageFileServiceImp;
import com.regulus.filedemo.service.RedisServiceImp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-10-27
 */

@Slf4j
@Component
public class ScheduledTask {

    @Resource
    private RedisServiceImp redisServiceImp;

    @Resource
    private ImageFileServiceImp fileService;


    // 每10秒检查一次过期
    @Scheduled(fixedRate = 1000)
    public void deleteMyMember() throws IOException {
        Set<String> members;
        members = redisServiceImp.getValue();
        if(members.isEmpty()) {
            //log.info("未检查到过期图片");
            return;
        }
        log.info("检查到过期图片：" + members);

        for (String member : members) {
            String[] parts = member.split("\\."); // 使用 '.' 进行分割，需要转义
            String fidTemple = parts[0]; // 获取 fid
            String uidTemple = parts[1]; // 获取 uid
            Long fid = Long.valueOf(fidTemple); // 将字符串转换为 Long
            List<Long> fids = new ArrayList<>();
            fids.add(fid); // 将 Long 添加到 List<Long>
            Long uid = Long.valueOf(uidTemple);
            fileService.deleteFile(fids,uid);
            redisServiceImp.deleteMember(member);
        }

    }



}
