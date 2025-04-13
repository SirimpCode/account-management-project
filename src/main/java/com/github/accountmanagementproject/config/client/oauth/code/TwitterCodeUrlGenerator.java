package com.github.accountmanagementproject.config.client.oauth.code;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
public class TwitterCodeUrlGenerator extends OAuthCodeUrlGenerator {
    @Value("${oauth.twitter.client-id}")
    private String clientId;
    @Value("${oauth.twitter.url.code}")
    private String codeUrl;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.TWITTER;
    }

    @Override
    protected UriComponents getParam(String redirectUrl) {
        return UriComponentsBuilder.newInstance()
                .queryParam("client_id", this.clientId)
                .queryParam("state", UUID.randomUUID())
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("response_type", "code")
                .queryParam("scope", "tweet.read users.read follows.read offline.access")
                .queryParam("code_challenge", "challenge")
                .queryParam("code_challenge_method", "plain")
                .build();
    }

    @Override
    protected String baseAuthCodeUrl() {
        return this.codeUrl + "/i/oauth2/authorize";
    }
}
