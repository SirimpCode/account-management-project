package com.github.accountmanagementproject.common.event;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import com.nimbusds.jose.util.StandardCharset;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Map;

import static com.github.accountmanagementproject.config.security.JwtProvider.AUTH_EXCEPTION_NAME;
import static com.github.accountmanagementproject.config.security.JwtProvider.AUTH_HEADER_NAME;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Map<Class<? extends Exception>, String> EXCEPTION_MESSAGES = Map.of(
            MalformedJwtException.class, "올바르지 않은 토큰",
            UnsupportedJwtException.class, "올바르지 않은 토큰",
            IllegalArgumentException.class, "올바르지 않은 토큰",
            ExpiredJwtException.class, "만료된 토큰",
            SignatureException.class, "잘못된 서명의 토큰",
            NullPointerException.class, "잘못된 서명의 토큰" // NPE는 서명 검증 과정에서 발생 가능
    );
    private static final String DEFAULT_ERROR_MESSAGE = "토큰 정보 없음";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // JwtFilter에서 설정한 예외 획득 (현재 코드 유지)
        Exception resolvedException = (Exception) request.getAttribute(AUTH_EXCEPTION_NAME);
        if (resolvedException == null) {// cause 확인 및 처리
            resolvedException = authException.getCause() instanceof Exception ? (Exception) authException.getCause() : authException;
        }

        String authorizationToken = request.getHeader(AUTH_HEADER_NAME);
        String systemErrorMessage = resolvedException != null ? resolvedException.getMessage() : authException.getMessage();
        String customErrorMessage = resolveCustomErrorMessage(resolvedException);

        CustomErrorResponse<String> errorResponse = generateErrorResponse(systemErrorMessage, customErrorMessage, authorizationToken);
        processingResponse(response, errorResponse);
        response.getWriter().flush(); // 데이터를 즉시 전송

    }

    private void processingResponse(HttpServletResponse response, CustomErrorResponse<String> errorResponse) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharset.UTF_8.name());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private String resolveCustomErrorMessage(Exception resolvedException) {
        if (resolvedException == null) return DEFAULT_ERROR_MESSAGE;
        return EXCEPTION_MESSAGES.getOrDefault(resolvedException.getClass(), DEFAULT_ERROR_MESSAGE);

    }
    private CustomErrorResponse<String> generateErrorResponse(String systemMessage, String customMessage, String request) {
        return CustomErrorResponse.<String>builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .systemMessage(systemMessage)
                .customMessage(customMessage)
                .request(request)
                .build();
    }

}