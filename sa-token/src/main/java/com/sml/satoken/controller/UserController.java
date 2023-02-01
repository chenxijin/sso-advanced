package com.sml.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sml.platform.base.Result;
import com.sml.satoken.mapper.UserMapper;
import com.sml.satoken.mapper.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserMapper userMapper;

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?account=ceshi&password=123456
    @RequestMapping("doLogin")
    public Result doLogin(String account, String password) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getAccount, account).eq(User::getEnable, true);
        User user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            return Result.error("账户或密码错误");
        }
        String pwd = user.getPassword();
        Assert.notBlank(password, "密码不能为空");
        boolean b = DigestUtil.bcryptCheck(password, pwd);
        if (b) {
            Long userId = user.getId();
            StpUtil.login(userId);
            return Result.success(StpUtil.getTokenValue());
        }
        return Result.error("账号或密码错误");
    }


    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }


    @RequestMapping("logout")
    public void logout() {
        StpUtil.logout();
    }

    public static void main(String[] args) {
        System.out.println(DigestUtil.bcrypt("123456"));
    }
}
