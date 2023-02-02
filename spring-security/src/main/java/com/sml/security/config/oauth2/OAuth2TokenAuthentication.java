package com.sml.security.config.oauth2;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Optional;

@Getter
public class OAuth2TokenAuthentication {

    private final OAuth2AccessToken token;

    private final OAuth2Authentication authentication;

    private final Authentication userAuthentication;

    public OAuth2TokenAuthentication(OAuth2AccessToken token, OAuth2Authentication authentication) {
        this.token = token;
        this.authentication = authentication;
        this.userAuthentication = Optional.ofNullable(authentication)
                .map(OAuth2Authentication::getUserAuthentication)
                .orElse(null);
    }
}
