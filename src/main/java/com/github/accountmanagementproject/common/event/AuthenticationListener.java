package com.github.accountmanagementproject.common.event;

import com.github.accountmanagementproject.common.AccountServiceModule;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.common.myenum.UserStatus;
import com.github.accountmanagementproject.service.account.auth.userdetails.CustomUserDetails;
import com.github.accountmanagementproject.common.exceptions.CustomBadCredentialsException;
import com.github.accountmanagementproject.web.dto.account.auth.response.AuthFailureMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {

    private final AccountServiceModule accountServiceModule;

    @EventListener
    public void handleAuthFailEvent(CustomAuthFailEvent event){
        MyUser myUser = accountServiceModule.failureCounting(event.getException().getUser().getMyUser());

        throw CustomBadCredentialsException.of()
                .systemMessage(myUser.getStatus() == UserStatus.LOCK?event.getException().getMessage()+" 계정이 잠깁니다."
                        :event.getException().getMessage())
                .customMessage("비밀번호 오류")
                .request(new AuthFailureMessage(myUser))
                .build();
    }

    @EventListener
    public void handleAuthSuccessEvent(AuthenticationSuccessEvent event){
        CustomUserDetails customUserDetails = (CustomUserDetails) event.getAuthentication().getPrincipal();

        accountServiceModule.loginSuccessEvent(customUserDetails.getMyUser());
    }
}


