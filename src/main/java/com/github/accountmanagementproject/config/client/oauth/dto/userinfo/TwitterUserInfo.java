package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.Getter;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterUserInfo implements OAuthUserInfo{

    private Data data;
    @Getter
    static class Data {
        private String id;
        private String name;
        private String email;
        private String username;
    }

    @Override
    public TwitterUserInfo updateEmailReturnThis(String email) {
        this.data.email = email;
        return this;
    }

    @Override
    public String getSocialId() {
        return this.data.id;
    }

    @Override
    public String getEmail() {
        return this.data.email;
    }

    @Override
    public String getNickname() {
        return this.data.name;
    }

    @Override
    public String getProfileImg() {
        return null;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.TWITTER;
    }
}
