package com.regulus.filedemo.exception;

public class AppException extends RuntimeException{

    private int code = 500;
    private String msg = "服务器异常";

    //这里只使用我们定义好的异常类型AppExceptionCodeMsg
    public AppException(AppExceptionCodeMsg appExceptionCodeMsg){
        super();
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();

    }

    //这个是备用的定义异常
    public AppException(int code, String msg){
        super();
        this.code = code;
        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
