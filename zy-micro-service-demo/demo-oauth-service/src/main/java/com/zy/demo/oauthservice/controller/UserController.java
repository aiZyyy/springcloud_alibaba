package com.zy.demo.oauthservice.controller;

import com.zy.demo.oauthservice.domain.UserDTO;
import com.zy.demo.oauthservice.holder.LoginUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZY
 * @date 2020/10/9 10:49
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public UserDTO currentUser() {
        return loginUserHolder.getCurrentUser();
    }

}