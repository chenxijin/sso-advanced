package com.sml.security.handler;

import cn.hutool.json.JSONUtil;
import com.sml.security.config.oauth2.OAuth2TokenAuthentication;
import com.sml.security.mapper.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RedisStoreTokenLogoutHandler implements LogoutHandler {

    private final String tokenStorePrefix;

    private final RedisTemplate<String, Object> beanRedisTemplate;

    private final TokenStore tokenStore;

    public RedisStoreTokenLogoutHandler(String tokenStorePrefix, RedisTemplate<String, Object> beanRedisTemplate, TokenStore tokenStore) {
        this.tokenStorePrefix = tokenStorePrefix;
        this.beanRedisTemplate = beanRedisTemplate;
        this.tokenStore = tokenStore;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return;
        }

        User user = (User) authentication.getPrincipal();

        String pattern = tokenStorePrefix + ":" + user.getId() + "*";
        Set<String> keys = beanRedisTemplate.keys(pattern);

        if (log.isDebugEnabled()) {
            log.debug("IDaaSRedisStoreTokenLogoutHandler current auth : {}", JSONUtil.toJsonStr(authentication));
        }

        Map<Authentication, List<OAuth2TokenAuthentication>> groupMap = keys.stream()
                .flatMap(key -> beanRedisTemplate.opsForList().range(key, 0, -1).stream())
                .map(accessToken -> (OAuth2AccessToken) accessToken)
                .map(accessToken -> new OAuth2TokenAuthentication(accessToken, tokenStore.readAuthentication(accessToken)))
                .filter(auth -> Objects.nonNull(auth.getUserAuthentication()))
                .collect(Collectors.groupingBy(OAuth2TokenAuthentication::getUserAuthentication));

        groupMap.get(authentication).forEach(auth -> {
            OAuth2AccessToken accessToken = auth.getToken();
            if (Objects.nonNull(accessToken.getRefreshToken())) {
                tokenStore.removeRefreshToken(accessToken.getRefreshToken());
            }
            tokenStore.removeAccessToken(accessToken);
        });

    }
}
