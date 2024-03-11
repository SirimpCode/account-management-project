package com.github.accountmanagementproject.web.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
