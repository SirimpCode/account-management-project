package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.NaverTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.NaverUserInfo;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class NaverApiClient extends OAuthApiClient {

    private final String grantType = "authorization_code";
    private final String authEndPoint = "/oauth2.0/token";
    private final String apiEndPoint = "/v1/nid/me";
    @Value("${oauth.naver.url.auth}")
    private String authUrl;

    @Value("${oauth.naver.url.api}")
    private String apiUrl;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.secret}")
    private String clientSecret;

    public NaverApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }


    @Override
    public OAuthProvider oAuthProvider(){
        return OAuthProvider.NAVER;
    }



    @Override
    protected Class<NaverTokens> getTokenClass() {
        return NaverTokens.class;
    }

    @Override
    protected Class<NaverUserInfo> getUserInfoClass() {
        return NaverUserInfo.class;
    }
}