package com.sml.security.handler;

import cn.hutool.json.JSONUtil;
import com.sml.security.config.redis.RedisUtils;
import com.sml.security.mapper.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class RedisStoreTokenLogoutHandler implements LogoutHandler {

    private final String tokenStorePrefix;

    private final RedisTemplate<String, Object> beanRedisTemplate;


    public RedisStoreTokenLogoutHandler(String tokenStorePrefix, RedisTemplate<String, Object> beanRedisTemplate) {
        this.tokenStorePrefix = tokenStorePrefix;
        this.beanRedisTemplate = beanRedisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return;
        }
        User user = (User) authentication.getPrincipal();
        if (log.isDebugEnabled()) {
            log.debug("IDaaSRedisStoreTokenLogoutHandler current auth : {}", JSONUtil.toJsonStr(authentication));
        }

        String pattern = tokenStorePrefix + ":" + user.getId() + "*";
        Set<String> keys = beanRedisTemplate.keys(pattern);
        RedisUtils.clearRedisList(keys);

    }
}
