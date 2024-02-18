//package com.github.accountmanagementproject.config.security.exception;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Collection;
//
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        boolean isAuth = !authorities.isEmpty();
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");
//        PrintWriter printWriter = response.getWriter();
//        CustomErrorResponse errorResponse = new CustomErrorResponse(
//                HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(),
//                isAuth?"접근 권한이 없습니다.":"계정에 권한이 설정되지 않았습니다.",
//                authorities.toString()
//        );
//        ObjectMapper objectMapper = new ObjectMapper();
//        String strResponse = objectMapper.writeValueAsString(errorResponse);
//        printWriter.println(strResponse);
//    }
//}