package com.github.accountmanagementproject.config.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAuth = !authorities.isEmpty();
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                new CustomErrorResponse.ErrorDetail(
                        HttpStatus.FORBIDDEN, accessDeniedException.getMessage(),
                        isAuth?"접근 권한이 없습니다.":"계정에 권한이 설정되지 않았습니다.",String.valueOf(authorities))
        );

        String strResponse = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(errorResponse);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(strResponse);
        printWriter.flush();
        printWriter.close();
    }


}