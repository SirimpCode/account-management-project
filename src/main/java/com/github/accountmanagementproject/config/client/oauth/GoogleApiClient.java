package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.GoogleTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.GoogleUserInfo;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class GoogleApiClient extends OAuthApiClient{
    private final String grantType = "authorization_code";
    private final String authEndPoint = "/token";
    private final String apiEndPoint = "/oauth2/v3/userinfo";


    @Value("${oauth.google.url.auth}")
    private String authUrl;

    @Value("${oauth.google.url.api}")
    private String apiUrl;

    @Value("${oauth.google.client-id}")
    private String clientId;
    @Value("${oauth.google.secret}")
    private String clientSecret;

    public GoogleApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    protected MultiValueMap<String, String> makeRequestBody(MultiValueMap<String, String> beingCreatedBody) {
        return beingCreatedBody;
    }

    @Override
    protected Class<GoogleTokens> getTokenClass() {
        return GoogleTokens.class;
    }

    @Override
    protected Class<GoogleUserInfo> getUserInfoClass() {
        return GoogleUserInfo.class;
    }
}
