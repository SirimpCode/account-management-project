package com.github.accountmanagementproject.service.account.oauth;

import com.github.accountmanagementproject.config.properties.server.ServerUrlFields;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.account.oauth.request.OAuthLoginParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OAuthProviderService {
    private final OAuthCodeManager oAuthCodeManager;
    private final RestTemplate restTemplate;
    private final Function<ServerUrlFields, String> requestBaseUrlProvider;

    private final String redirectApiUri = "/api/oauth/";


    public String getOAuthLoginPageUrl(OAuthProvider oAuthProvider, HttpServletRequest httpServletRequest) {
        String baseUrl = getRequestBaseUrl(httpServletRequest);
        String redirectUrl = baseUrl + redirectApiUri+oAuthProvider.name().toLowerCase()+"/callback";

        return oAuthCodeManager.getAuthorizationUrl(oAuthProvider, redirectUrl);
    }

    public ResponseEntity<String> requestOAuthLogin(OAuthProvider provider, OAuthLoginParams requestParams, HttpServletRequest httpServletRequest) {
        String loginApiUri = redirectApiUri + provider.name().toLowerCase();
        String baseUrl = getRequestBaseUrl(httpServletRequest);

        try {
            System.out.println("포스트요청 한번실행");
            return restTemplate.postForEntity(baseUrl+loginApiUri, requestParams, String.class);
        } catch (HttpClientErrorException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(ex.getResponseBodyAsString());
        }

    }

    private String getRequestBaseUrl(HttpServletRequest request) {
        ServerUrlFields fields = ServerUrlFields.fromRequest(request);
        return requestBaseUrlProvider.apply(fields);
    }
}
