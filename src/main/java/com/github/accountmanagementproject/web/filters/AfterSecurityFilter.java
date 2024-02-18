package com.github.accountmanagementproject.web.filters;

import com.github.accountmanagementproject.config.security.exception.ExceptionContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AfterSecurityFilter extends OncePerRequestFilter {
    @Override//비로그인 혹은 만료된 로그인으로 전체권한인 uri 이용시 쓰레드로컬 비우기
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("인증받은후에유"+ExceptionContextHolder.getExceptionMessage());
        ExceptionContextHolder.removeLocalThread();
        filterChain.doFilter(request,response);
    }
}
