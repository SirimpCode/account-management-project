package com.github.accountmanagementproject.web.dto.accountAuth.oauth.server;


import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;

import java.math.BigInteger;

public interface OAuthInfoResponse {
    BigInteger getSocialId();
    String getEmail();
    String getNickName();
    String getProfileImg();
    OAuthProvider getOAuthProvider();
}
