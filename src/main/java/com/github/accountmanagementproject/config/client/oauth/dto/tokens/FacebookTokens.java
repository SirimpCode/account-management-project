package com.github.accountmanagementproject.config.client.oauth.dto.tokens;

import lombok.Getter;
import org.springframework.util.MultiValueMap;

@Getter
public class FacebookTokens implements OAuthTokens {
    private String accessToken;
    private String tokenType;
    private String expiresIn;

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> beingCreatedBody = OAuthTokens.super.makeBody();
        beingCreatedBody.add("fields", "id,name,email,last_name,first_name,short_name,picture.type(large)");
        return beingCreatedBody;
    }
}
