package com.github.accountmanagementproject.config.client.oauth.code;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NaverCodeUrlGenerator extends OAuthCodeUrlGenerator {
    @Value( "${oauth.naver.client-id}")
    private String clientId;
    @Value("${oauth.naver.state}")
    private String state;
    @Value("${oauth.naver.url.auth}")
    private String authUrl;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    protected UriComponents getParam(String redirectUrl) {
//        String redirectUrl = super.getApiBaseUrl() + "/naver";
        return UriComponentsBuilder.newInstance()
                .queryParam("client_id", clientId)
                .queryParam("state", state)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("response_type", "code")
                .build();
    }

    @Override
    protected String baseAuthCodeUrl() {
        return authUrl + "/oauth2.0/authorize";
    }
}
