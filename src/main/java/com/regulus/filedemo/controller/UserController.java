package com.regulus.filedemo.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.regulus.filedemo.entity.Setting;
import com.regulus.filedemo.entity.User;
import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;
import com.regulus.filedemo.request.OptionRequest;
import com.regulus.filedemo.request.SettingRequest;
import com.regulus.filedemo.request.UpdateUserInfoRequest;
import com.regulus.filedemo.request.UserLoginRequest;
import com.regulus.filedemo.response.Resp;
import com.regulus.filedemo.scheduledtask.ScheduledTask;
import com.regulus.filedemo.service.SettingServiceImp;
import com.regulus.filedemo.service.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    SettingServiceImp settingServiceImp;

    @Autowired
    ScheduledTask scheduledTask;

    private final String USER_INFO = "userInfo";


    @RequestMapping("/doLogin")
    public Resp<String> doLogin(@RequestBody UserLoginRequest userLoginRequest) {

        User user0 = userServiceImp.userLogin(userLoginRequest.getUsername(),userLoginRequest.getPassword());

        StpUtil.login(user0.getUid());
        StpUtil.getSession().set(USER_INFO,user0);
        return Resp.success(StpUtil.getTokenValue());
    }

    @RequestMapping("/register")
    public Resp<String> userRegister(@RequestBody UserLoginRequest userRequest) {

        User user0 = userServiceImp.userRegister(userRequest.getUsername(),userRequest.getPassword(),userRequest.getMail());
        StpUtil.login(user0.getUid());
        StpUtil.getSession().set(USER_INFO,user0);
        return Resp.success(StpUtil.getTokenValue());
    }

    @RequestMapping("/getSetting")
    public Resp<SettingRequest> getSetting() {
        SettingRequest settingRequest = settingServiceImp.getSetting(Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success(settingRequest);
    }
    
    @RequestMapping("/setting")
    public Resp setting(@RequestBody SettingRequest settingRequest) {
        if(settingRequest == null)throw new AppException(AppExceptionCodeMsg.FAIL);
        if(settingRequest.getExif() != null && (settingRequest.getExif() > 1 || settingRequest.getExif() < 0))
            throw new AppException(AppExceptionCodeMsg.FAIL);

        Setting setting = new Setting();
        setting.setUid(Long.parseLong((String)StpUtil.getLoginId()));
        userServiceImp.userSetting(setting);
        return Resp.success();
    }

    @RequestMapping("/getOption")
    public Resp<OptionRequest> getOption() {
        OptionRequest optionRequest = userServiceImp.getUserOption(Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success(optionRequest);
    }

    @RequestMapping("/updateUserInfo")
    public Resp updateUserInfo(@RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        userServiceImp.updateUserInfo(updateUserInfoRequest,Long.parseLong((String)StpUtil.getLoginId()));
        return Resp.success();
    }

    @RequestMapping("/isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenValue());
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @RequestMapping("/getRole")
    public SaResult getRole() {
        // TODO: 其他按照这个来改，哪个什么逼satoken真的P用没有！！！！！
        return SaResult.data(StpUtil.getRoleList());
    }
}





