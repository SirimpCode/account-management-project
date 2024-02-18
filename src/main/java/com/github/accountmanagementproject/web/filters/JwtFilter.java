package com.github.accountmanagementproject.web.filters;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.accountmanagementproject.config.security.JwtTokenConfig;
import com.github.accountmanagementproject.config.security.exception.ExceptionContextHolder;
import com.github.accountmanagementproject.service.customExceptions.CustomAccessDenied;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenConfig jwtTokenConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("들어와유"+ExceptionContextHolder.getExceptionMessage());
        String token = StringUtils.hasText(request.getHeader("Authorization"))
                &&request.getHeader("Authorization").startsWith("Bearer ")
                ?request.getHeader("Authorization").split(" ")[1].trim()
                :null;
        //옵셔널 사용하는 방법과 3항연산자중 취향에 따라 선택 외부 메서드로 빼는 경우도 있음.
//        String token = Optional.ofNullable(request.getHeader("Authorization"))
//                .filter(t -> t.startsWith("Bearer "))
//                .map(t -> t.substring(7))
//                .orElse(null);

        if(token!=null){
            Authentication authentication = jwtTokenConfig.getAuthentication(token);
            if(authentication != null) SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    @Override//필터를 적용시키지 않을 url true 값이 배출되면 필터는 실행되지 않는다.
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/api/auth/sign-up");
    }
}