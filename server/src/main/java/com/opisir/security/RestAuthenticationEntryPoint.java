package com.opisir.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opisir.core.JsonResult;
import com.opisir.core.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: dingjn
 * @Desc: 未认证处理
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //验证为未登陆状态会进入此方法，认证错误
        log.info("认证失败：{}",authException.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        JsonResult unifyResponse = JsonResult.FAIL(ResultCode.AUTH_401, "请重新登录");
        printWriter.write(new ObjectMapper().writeValueAsString(unifyResponse));
        printWriter.flush();
    }
}
