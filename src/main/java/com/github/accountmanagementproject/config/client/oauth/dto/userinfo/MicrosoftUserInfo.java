package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftUserInfo implements OAuthUserInfo {
    private String sub;
    @JsonProperty("familyname")
    private String familyName;
    @JsonProperty("givenname")
    private String givenName;
    private String email;
    private String locale;
    private String picture;

    @Override
    public String getSocialId() {
        return this.sub;
    }

    @Override
    public String getNickname() {
        return this.familyName+this.givenName;
    }

    @Override
    public String getProfileImg() {
        return this.picture;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.MICROSOFT;
    }
}
