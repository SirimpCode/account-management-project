package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.GithubTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.GithubEmail;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.GithubUserInfo;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

@Getter(value = AccessLevel.PROTECTED)
@Component
public class GithubApiClient extends OAuthApiClient {

    private final String grantType = "authorization_code";
    private final String authEndPoint = "/login/oauth/access_token";
    private final String apiEndPoint = "/user";


    @Value("${oauth.github.url.auth}")
    private String authUrl;

    @Value("${oauth.github.url.api}")
    private String apiUrl;

    @Value("${oauth.github.client-id}")
    private String clientId;
    @Value("${oauth.github.secret}")
    private String clientSecret;


    public GithubApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GITHUB;
    }
    @Override
    protected OAuthUserInfo requestUserInfo(HttpEntity<?> request, String url) {
        OAuthUserInfo userInfo = super.requestUserInfo(request, url);
        if(userInfo.getEmail()==null)//공개 이메일이 없는경우 이메일 api 따로 호출
            return getPrivatePrimaryEmail(request, userInfo);
        return userInfo;
    }

    private String getPrivateEmailApiUrl() {
        return this.apiUrl+this.apiEndPoint+"/emails";
    }

    protected OAuthUserInfo getPrivatePrimaryEmail(HttpEntity<?> request, OAuthUserInfo userInfo){
        GithubEmail[] githubEmails = super.getRestTemplate().exchange(
                this.getPrivateEmailApiUrl(), HttpMethod.GET, request, GithubEmail[].class
        ).getBody();
        if(githubEmails==null)
            return userInfo.updateEmailReturnThis(UUID.randomUUID().toString());

        String primaryEmail = Arrays.stream(githubEmails).filter(GithubEmail::isPrimary).map(GithubEmail::getEmail).findAny().orElseThrow();
        return userInfo.updateEmailReturnThis(primaryEmail);

    }



    @Override
    protected Class<? extends OAuthTokens> getTokenClass() {
        return GithubTokens.class;
    }

    @Override
    protected Class<? extends OAuthUserInfo> getUserInfoClass() {
        return GithubUserInfo.class;
    }
}
