package com.opisir.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200,"SUCCESS"),
    FAIL(400,"FAIL"),
    AUTH_401(401,"未认证"),
    AUTH_403(403,"权限不足"),
    NOT_FOUND(404,"未找到资源"),
    SERVER_ERROR(500,"服务器异常");

    private int code;
    private String message;
}
