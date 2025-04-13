package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.Getter;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserInfo implements OAuthUserInfo {

    private Response response;
    private String message;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class Response {
        private String email;
        private String nickname;
        private String name;

        private String id;
        private String profileImage;
    }

    @Override
    public String getSocialId() {
        return this.response.id;
    }

    @Override
    public String getEmail() {
        return this.response.email;
    }

    @Override
    public String getNickname() {
        return this.response.nickname;
    }

    @Override
    public String getProfileImg() {
        return this.response.profileImage;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }

}