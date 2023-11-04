package com.regulus.filedemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.regulus.filedemo.entity.Setting;
import com.regulus.filedemo.entity.User;
import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;
import com.regulus.filedemo.mapper.SettingMapper;
import com.regulus.filedemo.mapper.UserMapper;
import com.regulus.filedemo.request.OptionRequest;
import com.regulus.filedemo.request.UpdateUserInfoRequest;
import com.regulus.filedemo.util.StringCheckerUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-10-30
 */

@Service
public class UserServiceImp {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SettingMapper settingMapper;

    public User userLogin(String username, String pwd){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username)
                .eq("password",pwd); // 针对字段少的情况
        User user0 = userMapper.selectOne(queryWrapper);

        if( user0 == null) {
            throw new AppException(AppExceptionCodeMsg.FAIL);
        }
        else{
            return user0;
        }
    }

    public User userRegister(String username, String pwd, String mail){

        StringCheckerUtil.isValidString(username,32);
        StringCheckerUtil.isValidString(mail,50);
        StringCheckerUtil.isValidString(pwd,32);

        User user = new User();
        user.setUsername(username);
        user.setPassword(pwd);
        user.setMail(mail);
        userMapper.insert(user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username)
                .eq("password",pwd).eq("mail",mail);

        User user0 = userMapper.selectOne(queryWrapper);

        if(user0 == null) throw new AppException(AppExceptionCodeMsg.FAIL);

        Setting setting = new Setting();
        setting.setUid(user0.getUid());
        settingMapper.insert(setting);


        return user0;
    }

    public  void userSetting(Setting setting){
        UpdateWrapper<Setting> wrapper = new UpdateWrapper<>();
        wrapper.eq("uid",setting.getUid());

        Setting originalSetting =  settingMapper.selectById(setting.getUid());

        if(setting.getExif() != null) {
            StringCheckerUtil.notEmptyChecker(setting.getExif() + "");
            wrapper.set("exif", setting.getExif());
        }
        else {
            wrapper.set("exif", originalSetting.getExif());
        }

        if(setting.getExpireTime() != null) {
            StringCheckerUtil.notEmptyChecker(setting.getExpireTime()+"");
            wrapper.set("expire_time", setting.getExpireTime());
        }
        else{
            wrapper.set("expire_time",originalSetting.getExpireTime());
        }

        settingMapper.update(null,wrapper);
    }

    public OptionRequest getUserOption(Long uid){
        UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("uid",uid);
        User user = userMapper.selectOne(userWrapper);
        if(user == null) throw new RuntimeException();
        Setting setting = userMapper.querySettingById(uid);
        if(setting == null) throw new RuntimeException();

        OptionRequest optionRequest = new OptionRequest();
        optionRequest.setUsername(user.getUsername());
        optionRequest.setMail(user.getMail());
        optionRequest.setExpireTime(setting.getExpireTime());
        if(setting.getExif() == 1)
        optionRequest.setIsExifDataKept(true);
        else if(setting.getExif() == 0)
            optionRequest.setIsExifDataKept(false);
        else throw new RuntimeException();

        return optionRequest;
    }

    public void updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest,Long uid){
        Setting setting = new Setting();
        setting.setUid(uid);
        if(updateUserInfoRequest.getIsExifDataKept() == null){

        }
        else if(updateUserInfoRequest.getIsExifDataKept())
        setting.setExif(1);
        else setting.setExif(0);
        setting.setExpireTime(updateUserInfoRequest.getExpireTime());
        userSetting(setting);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid)
                .eq("password",updateUserInfoRequest.getCurrentPassword());


        if(updateUserInfoRequest.getCurrentPassword() != null) {
            User user0 = userMapper.selectOne(queryWrapper);
            if (user0 == null) throw new AppException(AppExceptionCodeMsg.NO_AUTHENTICATION);
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("uid",setting.getUid());

        if(updateUserInfoRequest.getMail() != null) {
            StringCheckerUtil.notEmptyChecker(updateUserInfoRequest.getMail());
            updateWrapper.set("mail", updateUserInfoRequest.getMail());
        }

        if(updateUserInfoRequest.getUsername() != null) {
            StringCheckerUtil.notEmptyChecker(updateUserInfoRequest.getUsername());
            updateWrapper.set("username", updateUserInfoRequest.getUsername());
        }

        if(updateUserInfoRequest.getCurrentPassword() != null && updateUserInfoRequest.getNewPassword() != null) {
            StringCheckerUtil.notEmptyChecker(updateUserInfoRequest.getNewPassword());
            updateWrapper.set("password", updateUserInfoRequest.getNewPassword());
        }
        userMapper.update(null,updateWrapper);


    }


    public void UserInsert(User user){
        userMapper.insert(user);
    }

}
