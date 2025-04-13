package com.github.accountmanagementproject.config.client.oauth.code;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FacebookCodeUrlGenerator extends OAuthCodeUrlGenerator{

    @Value("${oauth.facebook.url.code}")
    private String codeUrl;
    @Value("${oauth.facebook.client-id}")
    private String clientId;


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.FACEBOOK;
    }

    @Override
    protected UriComponents getParam(String redirectUrl) {
        return UriComponentsBuilder.newInstance()
                .queryParam("client_id", this.clientId)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("scope", "email,public_profile") // 이메일, 공개 프로필 정보를 요청 작성안해도 기본적으로 요청됨
                .build();
    }

    @Override
    protected String baseAuthCodeUrl() {
        return this.codeUrl+"/v22.0/dialog/oauth";
    }
}
