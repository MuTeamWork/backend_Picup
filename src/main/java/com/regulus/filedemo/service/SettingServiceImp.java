package com.regulus.filedemo.service;

import com.regulus.filedemo.entity.Setting;
import com.regulus.filedemo.mapper.SettingMapper;
import com.regulus.filedemo.request.SettingRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImp {
    @Resource
    private SettingMapper settingMapper;
    public SettingRequest getSetting(Long uid){
        Setting setting = settingMapper.selectById(uid);
        SettingRequest settingRequest = new SettingRequest();
        settingRequest.setExif(setting.getExif());
        settingRequest.setExpireTime(setting.getExpireTime());
        return  settingRequest;
    }
}
