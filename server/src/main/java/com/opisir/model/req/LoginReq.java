package com.opisir.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Data
public class LoginReq {
    @NotBlank(message = "请输入用户名")
    private String username;
    @NotBlank(message = "请输入密码")
    private String password;

}
