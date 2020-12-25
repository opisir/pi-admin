package com.opisir.model.res;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Getter
@Setter
@Builder
public class LoginResp {

    private String accessToken;
    private String refreshToken;
}
