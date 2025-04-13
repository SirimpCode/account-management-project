package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookUserInfo implements OAuthUserInfo {
    private String id;
    private String name;
    private String email;
    private Picture picture;
    private String lastName;
    private String firstName;
    private String shortName;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Picture {
        private Data data;

            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)
            static class Data {
                private String url;
            }
    }

    @Override
    public String getSocialId() {
        return this.id;
    }

    @Override
    public String getNickname() {
        return this.name;
    }

    @Override
    public String getProfileImg() {
        return this.picture.data.url;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.FACEBOOK;
    }

}
