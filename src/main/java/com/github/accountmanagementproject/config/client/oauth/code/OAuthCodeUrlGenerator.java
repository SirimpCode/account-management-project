package com.github.accountmanagementproject.config.client.oauth.code;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.web.util.UriComponents;

public abstract class OAuthCodeUrlGenerator {

    public String getAuthorizationUrl(String redirectUrl) {
        return baseAuthCodeUrl() + getParam(redirectUrl).toUriString();
    }
    public abstract OAuthProvider oAuthProvider();

    protected abstract UriComponents getParam(String redirectUrl);
    protected abstract String baseAuthCodeUrl();
}
