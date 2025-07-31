package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.FacebookTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.FacebookUserInfo;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class FacebookApiClient extends OAuthApiClient {

    private final String grantType = "authorization_code";
    private final String authEndPoint = "/v22.0/oauth/access_token";
    private final String apiEndPoint = "/v22.0/me";

    @Value("${oauth.facebook.url.api}")
    private String apiUrl;
    @Value("${oauth.facebook.client-id}")
    private String clientId;
    @Value("${oauth.facebook.secret}")
    private String clientSecret;


    public FacebookApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected String getAuthUrl() {
        return this.apiUrl;
    }


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.FACEBOOK;
    }


    @Override
    protected Class<? extends OAuthTokens> getTokenClass() {
        return FacebookTokens.class;
    }

    @Override
    protected Class<? extends OAuthUserInfo> getUserInfoClass() {
        return FacebookUserInfo.class;
    }
}
