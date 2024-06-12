package com.github.accountmanagementproject.web.dto.accountAuth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tokenType;
    private String accessToken;
    private String refreshToken;
}
