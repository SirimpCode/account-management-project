package com.github.accountmanagementproject.config.client.dto.userInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserUserInfo implements OAuthUserInfo {
    private String id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    @JsonProperty("synched_at")
    private LocalDateTime synchedAt;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
        @JsonProperty("profile_image_url")
        private String profileImageUrl;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getSocialId() {
        return id;
    }

    @Override
    public String getProfileImg() {
        return kakaoAccount.profile.getProfileImageUrl();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
