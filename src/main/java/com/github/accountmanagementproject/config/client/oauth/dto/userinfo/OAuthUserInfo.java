package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface OAuthUserInfo {
    String getSocialId();
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthProvider getOAuthProvider();
    void setEmail(String email);
}
