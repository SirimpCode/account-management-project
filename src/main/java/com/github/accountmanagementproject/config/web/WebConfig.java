//package com.github.accountmanagementproject.config;
//
//import com.github.accountmanagementproject.web.filtersAndInterceptor.AfterSecurityInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AfterSecurityInterceptor())
//                .order(1)//낮을수록 먼저 호출
//                .addPathPatterns("/**")// 적용할 경로
//                .excludePathPatterns("/api/auth/sign-up","/error");//제외할 경로
//    }
//}
