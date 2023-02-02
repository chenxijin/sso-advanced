package com.sml.security.controller;

import com.sml.platform.base.Result;
import com.sml.security.mapper.entity.User;
import com.sml.security.service.LoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/user/login")
    public Result login(@Validated @RequestBody User in) {
        return Result.success(loginService.login(in));
    }
}
