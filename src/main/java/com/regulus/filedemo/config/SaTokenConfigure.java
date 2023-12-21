package com.regulus.filedemo.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * [Sa-Token 权限认证] 配置类
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {



     //注册 [Sa-Token 全局过滤器]
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()



                // 异常处理函数：每次认证函数发生异常时执行此函数
//                .setError(e -> {
//                    return SaResult.error(e.getMessage());
//                })

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    SaHolder.getResponse()

                            // ---------- 设置跨域响应头 ----------

                            .setHeader("Access-Control-Allow-Credentials","true")
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "*")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")


                    ;
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                            .back();


                })
                ;
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {

            // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
            SaRouter.match("/**",  r -> StpUtil.checkLogin());

            // 角色校验 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
            SaRouter.match("/user/**", r -> StpUtil.checkRoleOr("user", "super-admin"));

            // 权限校验 -- 不同模块校验不同权限
            // SaRouter.match("/user/**", r -> StpUtil.checkPermission("admin"));

        })).addPathPatterns("/**")
                .excludePathPatterns("/user/register","/user/doLogin","/image/*","/thu/*","/file/fileUpload");
    }
    }










