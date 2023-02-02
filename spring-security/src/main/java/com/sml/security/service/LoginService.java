package com.sml.security.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.sml.platform.base.Result;
import com.sml.platform.utils.JwtUtil;
import com.sml.security.config.redis.RedisUtils;
import com.sml.security.mapper.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    public Map login(User in) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(in.getAccount(), in.getPassword())
        );
        Assert.notNull(authenticate, "用户名或密码错误");

        User user = (User) authenticate.getPrincipal();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        RedisUtils.put("login:" + user.getId(), JSONUtil.toJsonStr(user));
        Map<String, String> result = new HashMap<>();
        result.put("token", jwt);
        return result;
    }
}

