package com.github.accountmanagementproject.service.account.auth.userdetails;

import com.github.accountmanagementproject.common.event.CustomAuthFailEvent;
import com.github.accountmanagementproject.common.exceptions.CustomBadCredentialsEventEx;
import com.github.accountmanagementproject.common.exceptions.CustomBadCredentialsException;
import com.github.accountmanagementproject.web.dto.account.auth.response.AuthFailureMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;  // 이벤트 발행용
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(principal);
        checkUserStatus(customUserDetails);

        if (passwordEncoder.matches(password, customUserDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(customUserDetails, password, customUserDetails.getAuthorities());
        }else {
            var ex = CustomBadCredentialsEventEx.of("자격 증명 실패", customUserDetails);
            eventPublisher.publishEvent(new CustomAuthFailEvent(authentication,ex));
            throw ex;
        }
    }
    private void checkUserStatus(CustomUserDetails customUserDetails) {
        if (!customUserDetails.isAccountNonExpired()) {
            throwCustomBadCredentialsException("만료된 계정입니다.", customUserDetails);
        } else if (!customUserDetails.isAccountNonLocked()) {
            throwCustomBadCredentialsException("잠긴 계정입니다.", customUserDetails);
        } else if (!customUserDetails.isEnabled()) {
            throwCustomBadCredentialsException("비활성화된 계정입니다.", customUserDetails);
        } else if (!customUserDetails.isCredentialsNonExpired()) {
            throwCustomBadCredentialsException("만료된 자격 증명입니다.", customUserDetails);
        }
    }
    private void throwCustomBadCredentialsException(String message, CustomUserDetails customUserDetails) {
        throw CustomBadCredentialsException.of()
                .customMessage(message)
                .request(new AuthFailureMessage(customUserDetails.getMyUser()))
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
