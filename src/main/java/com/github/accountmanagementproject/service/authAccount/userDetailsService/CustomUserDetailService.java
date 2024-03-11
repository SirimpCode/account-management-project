package com.github.accountmanagementproject.service.authAccount.userDetailsService;

import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.config.security.event.TestEvent;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;
import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.web.dto.account.AuthFailureMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AccountConfig accountConfig;

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) {
        MyUser myUser = accountConfig.findMyUserFetchJoin(emailOrPhoneNumber);
        checkLockedOrDisable(myUser);

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
