package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import lombok.Getter;

@Getter
public class TwitterTokens implements OAuthTokens {
    private String tokenType;
    private String expiresIn;
    private String scope;
    private String accessToken;
    private String refreshToken;
}
