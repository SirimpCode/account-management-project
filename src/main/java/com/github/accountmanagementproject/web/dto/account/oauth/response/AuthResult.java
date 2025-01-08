package com.github.accountmanagementproject.web.dto.account.oauth.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class AuthResult {
    private OAuthDtoInterface response;
    private HttpStatus httpStatus;
    private String message;
}
