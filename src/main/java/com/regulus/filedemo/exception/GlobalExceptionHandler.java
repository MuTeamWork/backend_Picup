package com.regulus.filedemo.exception;

import com.regulus.filedemo.response.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> Resp<T> exceptionHandler(Exception e){
        //这里先判断拦截到的Exception是不是我们自定义的异常类型
        if(e instanceof AppException){
            AppException appException = (AppException)e;
            log.error("Code：{},Exception: {}", appException.getCode(), appException.getMsg());
            return Resp.error(appException.getCode(),appException.getMsg());
        }

        //如果拦截的异常不是我们自定义的异常(例如：数据库主键冲突)
        log.info("Code：{},Exception: {}",500,"Server Error");
        log.error("Cause：{},Message: {}", e.getCause(), e.getMessage());//e.getCause(), e.getMessage()
        return Resp.error(500,"服务器端异常");
    }
}