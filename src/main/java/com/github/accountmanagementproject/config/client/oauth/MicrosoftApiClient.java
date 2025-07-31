package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.MicrosoftTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.MicrosoftUserInfo;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class MicrosoftApiClient extends OAuthApiClient{
    private final String grantType = "authorization_code";
    private final String authEndPoint = "/consumers/oauth2/v2.0/token";
    private final String apiEndPoint = "/oidc/userinfo";


    @Value("${oauth.microsoft.url.auth}")
    private String authUrl;
    @Value("${oauth.microsoft.url.api}")
    private String apiUrl;
    @Value("${oauth.microsoft.client-id}")
    private String clientId;
    @Value("${oauth.microsoft.secret}")
    private String clientSecret;

    public MicrosoftApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }
//
//    @Override
//    protected OAuthUserInfo requestUserInfo(HttpEntity<?> request, String url) {
//        return super.getRestTemplate().exchange(url, HttpMethod.GET, request,this.getUserInfoClass()).getBody();
//    }


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.MICROSOFT;
    }


    @Override
    protected Class<? extends OAuthTokens> getTokenClass() {
        return MicrosoftTokens.class;
    }

    @Override
    protected Class<? extends OAuthUserInfo> getUserInfoClass() {
        return MicrosoftUserInfo.class;
    }
}
