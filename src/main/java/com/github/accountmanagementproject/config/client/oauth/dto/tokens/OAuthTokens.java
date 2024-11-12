package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface OAuthTokens {
    String getAccessToken();
}
