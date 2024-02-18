package com.github.accountmanagementproject.config.security;

import com.github.accountmanagementproject.web.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
//웹요청과 응답이 시큐리티 필터체인을 거치게 해줌
@EnableWebSecurity
public class SecurityConfig {
//    private final JwtTokenConfig jwtTokenConfig;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c->c.disable())
                //이 설정은 현재 페이지의 출처와 동일한 출처를 가진 프레임만 로드될 수 있도록
                .headers(h->h.frameOptions(f->f.sameOrigin()))
                .cors(c->c.configurationSource(corsConfigurationSource()))
                // 보통 RESTful API에서 사용되며, JWT(Json Web Token)와 같은 토큰 기반의 인증 방식을 사용할 때 유용
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .exceptionHandling(e->{
//                    e.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//                    e.accessDeniedHandler(new CustomAccessDeniedHandler());
//                })
//                .authorizeHttpRequests(a-> a
//                        .requestMatchers("").hasRole("ADMIN")
//                        .requestMatchers("").hasAnyRole("USER","ADMIN")
//                        .requestMatchers("").permitAll());
//                .addFilterBefore(new JwtFilter(jwtTokenConfig), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        //set과 add의 차이 add는 하나씩추가 set은 통째로 설정
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        //응답에 노출되는 헤더
        corsConfiguration.addExposedHeader("Authorization");
        //요청에 허용되는 헤더
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(List.of("GET","PUT","POST","PATCH","DELETE","OPTIONS"));
        //사전요청 캐시 시간 보통 1시간으로 설정함
        corsConfiguration.setMaxAge(1000L*60*60);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
