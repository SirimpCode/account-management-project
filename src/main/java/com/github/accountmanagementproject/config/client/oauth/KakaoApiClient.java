package com.github.accountmanagementproject.config.client.oauth;

import com.github.accountmanagementproject.config.client.oauth.dto.tokens.KakaoTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.tokens.OAuthTokens;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.KakaoUserInfo;
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
public class KakaoApiClient extends OAuthApiClient {

    private final String grantType = "authorization_code";
    private final String authEndPoint = "/oauth/token";
    private final String apiEndPoint = "/v2/user/me";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.secret}")
    private String clientSecret;

    public KakaoApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }


    @Override
    protected MultiValueMap<String, String> makeRequestBody(MultiValueMap<String, String> beingCreatedBody){
        /* 사용자 정보 요청 값을 명시적으로 적어줄때
        beingCreatedBody.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"," +
                " \"kakao_account.name\", \"kakao_account.gender\", \"kakao_account.age_range\", \"kakao_account.birthday\"]");*/
        return beingCreatedBody;
    }


    @Override
    protected Class<KakaoTokens> getTokenClass() {
        return KakaoTokens.class;
    }

    @Override
    protected Class<KakaoUserInfo> getUserInfoClass() {
        return KakaoUserInfo.class;
    }
}
