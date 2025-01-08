package com.github.accountmanagementproject.config.client.oauth.code;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GithubCodeUrlGenerator extends OAuthCodeUrlGenerator {
    @Value( "${oauth.github.client-id}")
    private String clientId;
    @Value( "${oauth.github.url.auth}")
    private String authUrl;



    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GITHUB;
    }

    @Override
    protected UriComponents getParam(String redirectUrl) {
        return UriComponentsBuilder.newInstance()
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("scope", "user")
                .build();
    }

    @Override
    protected String baseAuthCodeUrl() {
        return authUrl+"/login/oauth/authorize";
    }
}
