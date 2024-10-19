package com.github.accountmanagementproject.config.client;

import com.github.accountmanagementproject.config.client.dto.userInfo.OAuthUserInfo;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.accountAuth.oauth.request.OAuthLoginParams;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthUserInfo requestOauthInfo(String accessToken);
}
