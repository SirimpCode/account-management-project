package com.github.accountmanagementproject.web.dto.account.oauth.request;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
    String getAuthorizationCode();

    static OAuthLoginParams fromCodeParams(OAuthCodeParams params, OAuthProvider provider) {
        return switch (provider) {
            case KAKAO -> KakaoLoginParams.of(params.getCode());
            case NAVER -> NaverLoginParams.of(params.getCode(), params.getState());
            case GOOGLE -> GoogleLoginParams.of(params.getCode(), params.getRedirectUri());
            case GITHUB -> GithubLoginParams.of(params.getCode());
        };
    }
}
