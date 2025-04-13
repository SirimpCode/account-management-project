package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import lombok.Getter;

@Getter
public class MicrosoftTokens implements OAuthTokens{
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String scope;
    private String refreshToken;
    private String idToken;
}
