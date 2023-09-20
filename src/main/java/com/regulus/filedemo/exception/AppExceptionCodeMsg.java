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
    FAIL(501, "failed"),
    REQUEST_ERROR(400, "Request Error"),
    NO_AUTHENTICATION(401, "No Authentication"),
    NO_AUTHORITIES(403, "No Authorities"),
    SERVER_ERROR(500, "Server Error"),
    TOO_MANY_REQUESTS(429, "Too Many Requests");

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
