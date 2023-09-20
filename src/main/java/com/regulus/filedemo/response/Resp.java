package com.regulus.filedemo.response;

import cn.hutool.json.JSONUtil;
import com.regulus.filedemo.entity.ImageInfo;
import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Resp<T> implements Serializable {

    //服务端返回的错误码
    private int status;
    //服务端返回的错误信息
    private String msg;
    //我们服务端返回的数据
    private T data;




    private Resp(int code, String msg, T data){
        this.status = code;
        this.msg = msg;
        this.data = data;
    }



    public static <T> Resp base(int code, String msg,T data){
        return new Resp(code, msg, data);
    }

    public static Resp success(){
        return new Resp(AppExceptionCodeMsg.SUCCESS.getCode(),
                AppExceptionCodeMsg.SUCCESS.getMsg(),null);
    }

    public static <T> Resp success(T data){
        return new Resp<>(AppExceptionCodeMsg.SUCCESS.getCode(),
                AppExceptionCodeMsg.SUCCESS.getMsg(),data);
    }

    public static <T> Resp error(AppExceptionCodeMsg appExceptionCodeMsg){
        Resp resp = new Resp(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
        return resp;
    }
    public static <T> Resp error(int code,String msg){
        Resp resp = new Resp(code,msg, null);
        return resp;
    }


}
