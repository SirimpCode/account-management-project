package com.github.accountmanagementproject.config.security.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                new CustomErrorResponse.ErrorDetail(
                        HttpStatus.UNAUTHORIZED,

                        Optional.ofNullable(ExceptionContextHolder.getExceptionMessage())
                                .map(map->map.get("systemMessage"))
                                .orElseGet(()->authException.getMessage()),

//getExceptionMessage() 부분에서 널포인트 익셉션이 발생하므로 여기서 한번끊어야됨
                        Optional.ofNullable(ExceptionContextHolder.getExceptionMessage())
                                .map(map->map.get("customMessage"))
                                .orElse("인증 토큰이 존재 하지 않습니다."),

                        request.getHeader("Authorization")
                )
        );

        ExceptionContextHolder.removeLocalThread();

        String strResponse = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(errorResponse);
        PrintWriter writer = response.getWriter();
        writer.println(strResponse);
        writer.flush();
        writer.close();
    }
}