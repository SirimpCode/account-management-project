package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.account.oauth.request.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
@RequiredArgsConstructor
public abstract class OAuthApiClient {

    private final RestTemplate restTemplate;

    public String requestAccessToken(OAuthLoginParams params) {
        String url = this.getAuthUrl()+this.getAuthEndPoint();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", this.getGrantType());
        body.add("client_id", this.getClientId());
        body.add("client_secret", this.getClientSecret());

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        OAuthTokens response = restTemplate.postForObject(url, request, this.getTokenClass());
        assert response != null;
        return response.getAccessToken();
    }

    public OAuthUserInfo requestOauthInfo(String accessToken) {
        String url = this.getApiUrl() + this.getApiEndPoint();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = this.makeRequestBody(new LinkedMultiValueMap<>());

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, this.getUserInfoClass());
    }

    public abstract OAuthProvider oAuthProvider();

    protected abstract MultiValueMap<String, String> makeRequestBody(MultiValueMap<String, String> beingCreatedBody);
    protected abstract String getApiUrl();
    protected abstract String getApiEndPoint();
    protected abstract String getAuthUrl();
    protected abstract String getAuthEndPoint();
    protected abstract String getGrantType();
    protected abstract String getClientId();
    protected abstract String getClientSecret();
    protected abstract Class<? extends OAuthTokens> getTokenClass();
    protected abstract Class<? extends OAuthUserInfo> getUserInfoClass();

}
