package com.github.accountmanagementproject.config.security.event;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import com.github.accountmanagementproject.web.filtersAndInterceptor.JwtFilter;
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



public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");

        Object exception = request.getAttribute(JwtFilter.AUTH_EXCEPTION);

        String[] makeStr = new String[3];
        makeStr[0] = request.getHeader("Authorization");
        makeStr[1] = exception==null?authException.getMessage():((RuntimeException) exception).getMessage();

        if (exception instanceof MalformedJwtException || exception instanceof UnsupportedJwtException || exception instanceof IllegalArgumentException) {
            makeStr[2] = "올바르지 않은 토큰";
        }else if(exception instanceof ExpiredJwtException){
            makeStr[2] = "만료된 토큰";
        } else if (exception instanceof SignatureException || exception instanceof NullPointerException) {
            makeStr[2] = "잘못된 서명의 토큰";
        } else makeStr[2] = "토큰 정보 없음";


        response.getWriter().println(makeResponse(makeStr));
    }

    public String makeResponse(String[] makeStr) throws JsonProcessingException {

        CustomErrorResponse errorResponse = new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .systemMessage(makeStr[1])
                .customMessage(makeStr[2])
                .request(makeStr[0])
                .build();

        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(errorResponse);
    }
}