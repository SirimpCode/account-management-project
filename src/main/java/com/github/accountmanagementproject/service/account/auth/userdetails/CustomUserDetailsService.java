package com.github.accountmanagementproject.service.account.auth.userdetails;

import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.service.exceptions.AccountLockedException;
import com.github.accountmanagementproject.service.exceptions.CustomAccessDenied;
import com.github.accountmanagementproject.web.dto.account.auth.response.AuthFailureMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountConfig accountConfig;
    private final HttpServletRequest request;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) {
        MyUser myUser = accountConfig.findMyUserFetchJoin(emailOrPhoneNumber);
        checkLockedOrDisable(myUser);

        HttpSession session = request.getSession();
        session.setAttribute("myUser", myUser);

        return User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .authorities(myUser.getRoles().stream()
                        .map(roles->new SimpleGrantedAuthority(roles.getName().name()))
                        .collect(Collectors.toSet())
                )
//                .accountLocked(myUser.isLocked() && !myUser.isUnlockTime())
//                .disabled(myUser.isDisabled())
                .build();
    }

    private void checkLockedOrDisable(MyUser myUser) {
        if (myUser.isLocked() && !myUser.isUnlockTime()) {
            throw new AccountLockedException.ExceptionBuilder()
                    .customMessage("로그인 실패")
                    .request(new AuthFailureMessage(myUser))
                    .build();
        } else if (myUser.isDisabled()){
            throw new CustomAccessDenied.ExceptionBuilder()
                    .systemMessage("시스템")
                    .customMessage("로그인 실패")
                    .request(new AuthFailureMessage(myUser))
                    .build();
        }
    }
}
