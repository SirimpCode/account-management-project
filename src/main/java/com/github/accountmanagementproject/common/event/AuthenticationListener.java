package com.github.accountmanagementproject.common.event;

import com.github.accountmanagementproject.common.AccountServiceModule;
import com.github.accountmanagementproject.common.exceptions.CustomBadCredentialsException;
import com.github.accountmanagementproject.common.myenum.UserStatus;
import com.github.accountmanagementproject.repository.account.user.MyUser;
import com.github.accountmanagementproject.common.security.userdetails.CustomUserDetails;
import com.github.accountmanagementproject.web.dto.account.auth.response.AuthFailureMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {

    private final AccountServiceModule accountServiceModule;



    @EventListener
    public void handleAuthFailEvent(AuthenticationFailureBadCredentialsEvent event){
        CustomUserDetails userDetails = (CustomUserDetails) event.getException().getAuthenticationRequest().getPrincipal();
        accountServiceModule.failureCounting(userDetails);
        throw CustomBadCredentialsException.of()
                .systemMessage(userDetails.getStatus() == UserStatus.LOCK?event.getException().getMessage()+" 계정이 잠깁니다."
                        :event.getException().getMessage())
                .customMessage("비밀번호 오류")
                .request(new AuthFailureMessage(userDetails))
                .build();
    }
//    @EventListener
//    public void handleAuthFailEvent(CustomAuthFailEvent event){
//        CustomUserDetails userDetails = accountServiceModule.failureCounting(event.getException().getUser());
//
//        throw CustomBadCredentialsException.of()
//                .systemMessage(userDetails.getStatus() == UserStatus.LOCK?event.getException().getMessage()+" 계정이 잠깁니다."
//                        :event.getException().getMessage())
//                .customMessage("비밀번호 오류")
//                .request(new AuthFailureMessage(userDetails))
//                .build();
//    }

    @EventListener
    public void handleAuthSuccessEvent(AuthenticationSuccessEvent event){
        CustomUserDetails customUserDetails = (CustomUserDetails) event.getAuthentication().getPrincipal();

        accountServiceModule.loginSuccessEvent(customUserDetails);
    }
}


