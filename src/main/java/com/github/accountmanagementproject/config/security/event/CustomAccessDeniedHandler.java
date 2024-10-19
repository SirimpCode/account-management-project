package com.github.accountmanagementproject.config.security.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.web.dto.responseSystem.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        CustomErrorResponse errorResponse = new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.FORBIDDEN)
                .systemMessage(accessDeniedException.getMessage())
                .customMessage("접근 권한 없음")
                .request(authorities.stream()
                        .map(authority->authority.getAuthority())
                        .map(roles-> RolesEnum.valueOf(roles).getValue())
                        .collect(Collectors.joining(",")))
                .build();

        String strResponse = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(errorResponse);
        response.getWriter().println(strResponse);

    }


}