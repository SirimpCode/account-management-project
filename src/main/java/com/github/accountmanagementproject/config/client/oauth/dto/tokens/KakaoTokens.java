package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import lombok.Getter;

@Getter
public class KakaoTokens implements OAuthTokens {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String expiresIn;
    private String refreshTokenExpiresIn;
    private String scope;
}