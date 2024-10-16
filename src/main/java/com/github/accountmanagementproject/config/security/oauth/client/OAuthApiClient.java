package com.github.accountmanagementproject.config.security.oauth.client;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.accountAuth.oauth.client.OAuthLoginParams;
import com.github.accountmanagementproject.web.dto.accountAuth.oauth.server.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
