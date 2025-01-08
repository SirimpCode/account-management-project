package com.github.accountmanagementproject.service.account.oauth;

import com.github.accountmanagementproject.config.client.oauth.code.OAuthCodeUrlGenerator;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthCodeManager {
    private final Map<OAuthProvider, OAuthCodeUrlGenerator> codeUris;

    public OAuthCodeManager(List<OAuthCodeUrlGenerator> codeContexts) {
        this. codeUris = codeContexts.stream().collect(
                Collectors.toUnmodifiableMap(OAuthCodeUrlGenerator::oAuthProvider, Function.identity())
        );
    }

    public String getAuthorizationUrl(OAuthProvider oAuthProvider, String redirectUrl) {
        return codeUris.get(oAuthProvider).getAuthorizationUrl(redirectUrl);
    }

}
