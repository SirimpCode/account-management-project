//package com.github.accountmanagementproject.config.security.exception;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Component
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        boolean isToken = request.getHeader("Token")!=null;
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");
//        PrintWriter writer = response.getWriter();
//        CustomErrorResponse errorResponse = new CustomErrorResponse()
//                (HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
//                        isToken?"로그인이 만료 되었습니다.":"로그인이 필요 합니다.",
//                        request.getHeader("Token"));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String strResponse = objectMapper.writeValueAsString(errorResponse);
//        writer.println(strResponse);
//    }
//}