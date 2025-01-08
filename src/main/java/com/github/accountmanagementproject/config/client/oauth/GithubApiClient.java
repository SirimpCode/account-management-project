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
import org.springframework.util.MultiValueMap;
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


    private String getPrivateEmailApiUrl() {
        return apiUrl+apiEndPoint+"/emails";
    }
    @Override
    protected String getPrivatePrimaryEmail(RestTemplate restTemplate, HttpEntity<?> request){
        GithubEmail[] githubEmails = restTemplate.exchange(
                this.getPrivateEmailApiUrl(), HttpMethod.GET, request, GithubEmail[].class
        ).getBody();
        if(githubEmails==null) return UUID.randomUUID().toString();
        return Arrays.stream(githubEmails).filter(GithubEmail::isPrimary).map(GithubEmail::getEmail).findAny().orElseThrow();

    }

    @Override
    protected MultiValueMap<String, String> makeRequestBody(MultiValueMap<String, String> beingCreatedBody) {
        return beingCreatedBody;
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
