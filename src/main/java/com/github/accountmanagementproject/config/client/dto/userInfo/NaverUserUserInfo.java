package com.github.accountmanagementproject.config.client.dto.userInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserUserInfo implements OAuthUserInfo {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String nickname;
        private String name;

        private String id;
        @JsonProperty("profile_image")
        private String profileImage;
    }

    @Override
    public String getSocialId() {
        return response.id;
    }

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public String getNickname() {
        return response.nickname;
    }

    @Override
    public String getProfileImg() {
        return response.profileImage;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }
}