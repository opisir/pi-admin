package com.opisir.core;

import com.alibaba.fastjson.JSONObject;
import com.opisir.model.entity.User;
import com.opisir.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Aspect
@Component
@Slf4j
public class ApiAspect {

    @Pointcut("execution(public * com.opisir.api..*.*(..)))")
    public void requestCutPoint() {

    }

    @Around("requestCutPoint()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable {

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //请求url
        // 记录下请求内容
        User user = AuthUtil.getCurrentUser();
        if (user == null) {
            user = new User(0, "", "未登录", true, null);
        }
        log.info("################ api日志开始 ################");
        log.info("【请求用户】 id={}, phone={} : ", user.getId(), user.getUsername());
        log.info("【请求URL】 : " + request.getRequestURL().toString());
        log.info("【请求方式】 : " + request.getMethod());
        log.info("【请求IP】 : " + request.getRemoteAddr() + " , nginx配置的ip：" + request.getHeader("X-REAL-IP"));
        Object[] args = call.getArgs();
        if (args != null) {
            StringBuilder sb = new StringBuilder("userId=" + user.getId() + "|");
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    continue;
                } else if (arg instanceof HttpServletResponse) {
                    continue;
                }
                sb.append(arg);
                sb.append("|");
            }
            log.info("【请求参数】: " + sb);
        }

        Long startTimeStamp = System.currentTimeMillis();
        try {
            // 请求处理
            Object object = call.proceed();
            log.info("【响应结果】: " + (null != object ? JSONObject.toJSONString(object) : null));
            return object;
        } catch (ServiceException se) {
            JsonResult fail = JsonResult.FAIL(se.getCode(), se.getMessage());
            log.error("【响应结果】(ServerException): " + JSONObject.toJSONString(fail));
            return fail;
        } catch (Exception e) {
            log.error("服务请求处理异常", e);
            JsonResult fail = JsonResult.FAIL(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMessage());
            log.error("【响应结果】(Exception): " + JSONObject.toJSONString(fail));
            return fail;
        } finally {
            log.info("################ api日志结束 耗时: {}毫秒 ################", (System.currentTimeMillis() - startTimeStamp));
        }
    }
}
