package com.github.accountmanagementproject.web.dto.account.oauth.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Getter
@NoArgsConstructor
public class GoogleLoginParams implements OAuthLoginParams{
    private String authorizationCode;
    private String redirectUri;
    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        String decodedCode = URLDecoder.decode(authorizationCode, StandardCharsets.UTF_8);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", decodedCode);
        body.add("redirect_uri", redirectUri);
        return body;
    }

}
