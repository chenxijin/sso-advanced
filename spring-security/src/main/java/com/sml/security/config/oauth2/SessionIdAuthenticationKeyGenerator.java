package com.sml.security.config.oauth2;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class SessionIdAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        if(authentication.getUserAuthentication().getDetails() instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getUserAuthentication().getDetails();
            return details.getSessionId() + ":" + super.extractKey(authentication);
        }

        return super.extractKey(authentication);
    }

}
