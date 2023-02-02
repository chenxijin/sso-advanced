package com.sml.springsecurity.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
