package com.opisir.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> {
    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    public static <T> JsonResult SUCC(T data) {
        return JsonResult.builder()
                .code(ResultCode.SUCCESS.getCode())
                .success(true)
                .message(ResultCode.SUCCESS.getMessage())
                .data(data).build();
    }

    public static JsonResult SUCC(String message) {
        return JsonResult.builder()
                .code(ResultCode.SUCCESS.getCode())
                .success(true)
                .message(message).build();
    }

    public static JsonResult FAIL(String message) {
        return JsonResult.builder()
                .code(ResultCode.FAIL.getCode())
                .success(false)
                .message(message).build();
    }

    public static JsonResult FAIL(ResultCode resultCode, String message) {
        return JsonResult.builder()
                .code(resultCode.getCode())
                .success(false)
                .message(message).build();
    }

    public static JsonResult FAIL(int code, String message) {
        return JsonResult.builder()
                .code(code)
                .success(false)
                .message(message).build();
    }

}
