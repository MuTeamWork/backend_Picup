package com.regulus.filedemo.exception;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-09-20
 */
public enum AppExceptionCodeMsg {
    SUCCESS(200, "success"),
    FAIL(501, "失败"),
    REQUEST_ERROR(400, "Request Error"),
    NO_AUTHENTICATION(401, "验证失败, 用户名或密码错误"),
    NO_AUTHORITIES(403, "No Authorities"),
    SERVER_ERROR(500, "Server Error"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    FILE_NOT_EXIST(400,"file doesn't exist"),
    USERNAME_NOT_NULL(1002,"用户名不得为空"),
    MAIL_NOT_NULL(1003,"邮箱不得为空"),
    PASSWORD_NOT_ULL(1004,"密码不得为空"),
    REQUEST_NOT_EMPTY(411,"字段需要有效长度"),
    UNPROCESSABLE_ENTITY(422,"请求数据不合法"),
    Double_ERROR(401, "用户名已存在"),;



    private final int code;
    private final String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    AppExceptionCodeMsg(int code, String description) {
        this.code = code;
        this.msg = description;
    }
}
