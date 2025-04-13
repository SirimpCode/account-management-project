package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo implements OAuthUserInfo {
    private String id;
    private KakaoAccount kakaoAccount;
    private LocalDateTime connectedAt;
    private LocalDateTime synchedAt;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class KakaoProfile {
        private String nickname;
        private String profileImageUrl;
    }

    @Override
    public String getEmail() {
        return this.kakaoAccount.email;
    }

    @Override
    public String getNickname() {
        return this.kakaoAccount.profile.nickname;
    }

    @Override
    public String getSocialId() {
        return this.id;
    }

    @Override
    public String getProfileImg() {
        return this.kakaoAccount.profile.getProfileImageUrl();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }

}
