package com.regulus.filedemo.saToken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.regulus.filedemo.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {


    // 返回一个账号所拥有的权限码集合
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }


     // 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User info = (User) StpUtil.getSession().get("userInfo");
        List<String> list = new ArrayList<String>();
        if(info.getRole() == 0) {
            list.add("user");
        } else if(info.getRole() == 1)
        list.add("admin");
        return list;
    }

}
