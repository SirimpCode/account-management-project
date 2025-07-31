package com.github.accountmanagementproject.web.dto.account.oauth.request;

import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
@Getter
@AllArgsConstructor(staticName = "of")
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
