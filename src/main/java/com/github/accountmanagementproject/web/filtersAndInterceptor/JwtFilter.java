package com.github.accountmanagementproject.web.filtersAndInterceptor;

import com.github.accountmanagementproject.config.security.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    public static final String AUTH_EXCEPTION = "auth-exception";


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

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
            try {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                request.setAttribute(AUTH_EXCEPTION, e);
            }
        }
        filterChain.doFilter(request, response);
    }


    @Override//필터를 적용시키지 않을 url true 값이 배출되면 필터는 실행되지 않는다.
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains("/swagger")
                ||request.getRequestURI().contains("/api-docs")
                ||request.getRequestURI().equals("/api/auth/sign-up")
                ||request.getRequestURI().equals("/api/auth/login");
    }
}