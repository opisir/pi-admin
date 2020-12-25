package com.opisir.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public JsonResult handle(ServiceException ex) {
        log.info("ServiceException:{}", ex.getMessage());
        return JsonResult.FAIL(ex.getCode(), ex.getMessage());
    }
}
