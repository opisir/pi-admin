package com.opisir.api;

import com.opisir.core.JsonResult;
import com.opisir.model.req.LoginReq;
import com.opisir.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Api(tags = "登录相关")
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public JsonResult login(@Valid @RequestBody LoginReq loginReq) {
        return JsonResult.SUCC(userService.login(loginReq));
    }
}
