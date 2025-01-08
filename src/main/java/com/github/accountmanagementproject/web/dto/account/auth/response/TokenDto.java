package com.github.accountmanagementproject.web.dto.account.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthDtoInterface;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto implements OAuthDtoInterface {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "토큰 타입은 필수 입니다.")
    @Schema(description = "토큰 타입", example = "Bearer")
    private String tokenType;
    @NotBlank(message = "액세스 토큰은 필수 입니다.")
    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjkwNjA0NjcsImV4cCI6MTcyOTA2NDA2Nywic3ViIjoiYWJjM0BhYmMuY29tIiwicm9sZXMiOiJST0xFX1VTRVIifQ.LeC81cXhFI1H_VlKcJlOzRmtR73ITIjqYdOsrPZqPZs")
    private String accessToken;
    @NotBlank(message = "리프레시 토큰은 필수 입니다.")
    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjkwNjA0NjcsImV4cCI6MTcyOTA2MTA2N30.y4lrehsGYXDBYM1i92LlGTkg2MbYmkoRt5baWHjh5bg")
    private String refreshToken;

}
