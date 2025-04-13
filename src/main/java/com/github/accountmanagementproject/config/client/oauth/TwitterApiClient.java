package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.tokens.TwitterTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.TwitterUserInfo;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class TwitterApiClient extends OAuthApiClient{
    private final String grantType = "authorization_code";
    private final String authEndPoint = "/2/oauth2/token";
    private final String apiEndPoint = "/2/users/me";
    @Value("${oauth.twitter.url.api}")
    private String apiUrl;
    @Value("${oauth.twitter.client-id}")
    private String clientId;
    @Value("${oauth.twitter.secret}")
    private String clientSecret;

    public TwitterApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected OAuthUserInfo requestUserInfo(HttpEntity<?> request, String url) {
        OAuthUserInfo userInfo = super.getRestTemplate().exchange(url, HttpMethod.GET, request,this.getUserInfoClass()).getBody();
        assert userInfo != null;//트위터는 email을 엑세스할 방법이 없음 랜덤값 임시 지정
        return userInfo.updateEmailReturnThis(UUID.randomUUID().toString());
    }

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.TWITTER;
    }


    @Override
    public String getAuthUrl(){
        return this.apiUrl;
    }

    @Override
    protected Class<? extends OAuthTokens> getTokenClass() {
        return TwitterTokens.class;
    }

    @Override
    protected Class<? extends OAuthUserInfo> getUserInfoClass() {
        return TwitterUserInfo.class;
    }

}
