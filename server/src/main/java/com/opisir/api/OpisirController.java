package com.opisir.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Api(tags = "OpisirController")
@RestController
@RequestMapping("/opisir")
public class OpisirController {

    @ApiOperation("dear")
    @GetMapping("/dear")
    public String dear(){
        return "My dear pi.";
    }
}
