package com.opisir.core;

import lombok.Getter;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Getter
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.FAIL.getCode();
    }

    public ServiceException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }
}
