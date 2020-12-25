package com.opisir.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: dingjn
 * @Desc:
 */
@RestController
@RequestMapping("/opisir")
public class OpisirController {

    @GetMapping("/dear")
    public String dear(){
        return "My dear pi.";
    }
}
