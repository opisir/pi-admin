package com.opisir.service;

import com.opisir.core.ServiceException;
import com.opisir.model.entity.User;
import com.opisir.model.req.LoginReq;
import com.opisir.model.res.LoginResp;
import com.opisir.repository.UserRepo;
import com.opisir.utils.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserRepo userRepo;

    public LoginResp login(LoginReq loginReq) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
            Authentication authenticate = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (DisabledException disabledException) { //当表中enabled为0时
            throw new ServiceException("账户不可用");
        } catch (AuthenticationException ex) {
            throw new ServiceException("用户名或密码不正确");
        }
        User user = userRepo.findByUsername(loginReq.getUsername());
        return tokenUtil.generatedToken(user);
    }
}
