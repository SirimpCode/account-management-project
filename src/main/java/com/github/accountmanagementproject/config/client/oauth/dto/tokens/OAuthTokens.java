package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface OAuthTokens {
    String getAccessToken();

    default MultiValueMap<String, String> makeBody(){
        return new LinkedMultiValueMap<>();
    }
}
