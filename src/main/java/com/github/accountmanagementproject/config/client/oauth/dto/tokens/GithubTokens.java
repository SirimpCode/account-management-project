package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import lombok.Getter;

@Getter
public class GithubTokens implements OAuthTokens{
    private String accessToken;
    private String tokenType;
    private String scope;
}
