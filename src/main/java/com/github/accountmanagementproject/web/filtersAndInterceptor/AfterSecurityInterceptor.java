//package com.github.accountmanagementproject.web.filtersAndInterceptor;
//
//import com.github.accountmanagementproject.config.security.exception.ExceptionContextHolder;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.io.IOException;
//
//public class AfterSecurityInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication.getPrincipal().equals("anonymousUser")) ExceptionContextHolder.removeLocalThread();
//        System.out.println("인터셉터 나가유 "+ExceptionContextHolder.getExceptionMessage());
//        return true;
//    }
//
////    @Override//비로그인 혹은 만료된 로그인으로 전체권한인 uri 이용시 쓰레드로컬 비우기
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
////        System.out.println("인증받은후에유"+ExceptionContextHolder.getExceptionMessage());
////        ExceptionContextHolder.removeLocalThread();
////        filterChain.doFilter(request,response);
////    }
//}
