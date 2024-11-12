package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import lombok.Getter;

@Getter
public class NaverTokens implements OAuthTokens {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String expiresIn;
}