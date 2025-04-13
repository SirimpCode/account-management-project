package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.account.AccountDefaultValueInterface;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface OAuthUserInfo extends AccountDefaultValueInterface {
    String getSocialId();
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthProvider getOAuthProvider();

    default OAuthUserInfo updateEmailReturnThis(String email) {
        throw new UnsupportedOperationException("This method is not supported.");
    }
}
