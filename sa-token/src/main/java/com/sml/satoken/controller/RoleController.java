package com.sml.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/role/")
public class RoleController {

    @GetMapping({"/{loginId}", "/roles"})
    public List<String> roles(Long loginId) {
        return Objects.isNull(loginId) ? StpUtil.getRoleList() : StpUtil.getRoleList(loginId);
    }

}
