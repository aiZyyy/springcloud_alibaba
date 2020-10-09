package com.zy.demo.oauthservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZY
 * @date 2020/9/22 11:02
 * @Description:
 * @Version 1.0
 */
@RestController
public class DemoOauthController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World.";
    }
}
