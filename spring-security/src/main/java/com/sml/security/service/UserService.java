package com.sml.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sml.security.mapper.MenuMapper;
import com.sml.security.mapper.UserMapper;
import com.sml.security.mapper.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserService implements UserDetailsService {
    @Resource
    UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        user.setPermissions(menuMapper.selectPerms(user.getId()));
        return user;
    }
}
