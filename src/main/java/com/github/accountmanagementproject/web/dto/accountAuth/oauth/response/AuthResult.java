package com.github.accountmanagementproject.web.dto.accountAuth.oauth.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class AuthResult<T> {
    private T response;
    private HttpStatus httpStatus;
    private String message;
}
