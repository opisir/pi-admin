package com.opisir.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opisir.config.AppProperties;
import com.opisir.model.entity.User;
import com.opisir.repository.UserRepo;
import com.opisir.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = request.getHeader(appProperties.getJwt().getHeader());
        if (StringUtils.isNotBlank(token) && token.startsWith(appProperties.getJwt().getPrefix())) {
            token = request.getHeader(appProperties.getJwt().getHeader()).replace(appProperties.getJwt().getPrefix(), "");//提取真实jwt
            String username = tokenUtil.getUsernameFromToken(token);//从jwt提取用户
//            if (username == null) {
//                returnJson(response, JsonResult.FAIL(ResultCode.AUTH_401, "token无效，重新登录"));
//            }
            if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepo.findByUsername(username);
                if (user != null && tokenUtil.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("username为{}的用户通过JWT认证过滤器", username);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    protected void returnJson(HttpServletResponse response,
                              Object data) {
        //将实体对象转换为JSON Object转换
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(new ObjectMapper().writeValueAsString(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
