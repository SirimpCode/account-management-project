package com.github.accountmanagementproject.config.client.oauth.code;

import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MicrosoftCodeUrlGenerator extends OAuthCodeUrlGenerator {

    @Value("${oauth.microsoft.url.auth}")
    private String authUrl;
    @Value("${oauth.microsoft.client-id}")
    private String clientId;


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.MICROSOFT;
    }

    @Override
    protected UriComponents getParam(String redirectUrl) {
        return UriComponentsBuilder.newInstance()
                .queryParam("client_id", this.clientId)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid profile email User.Read offline_access")
                .build();
    }


    @Override
    protected String baseAuthCodeUrl() {
        return this.authUrl+"/consumers/oauth2/v2.0/authorize";
    }
}
