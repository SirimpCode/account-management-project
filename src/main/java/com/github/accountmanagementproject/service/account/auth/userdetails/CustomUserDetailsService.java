package com.github.accountmanagementproject.service.account.auth.userdetails;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersRepository;
import com.github.accountmanagementproject.common.exceptions.CustomBadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MyUsersRepository myUsersRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String emailOrPhoneNumber) {
        MyUser myUser = myUsersRepository.findByEmailOrPhoneNumber(emailOrPhoneNumber)
                .orElseThrow(() -> CustomBadCredentialsException.of()
                        .systemMessage("User Not Found")
                        .customMessage("존재 하지 않는 유저 입니다.")
                        .build());

        return CustomUserDetails.of(myUser);
    }
/*CustomAuthenticationProvider 사용으로 주석처리
    private void checkLockedOrDisable(MyUser myUser) {
        if (myUser.isLocked() && !myUser.isUnlockTime()) {
            throw AccountLockedException.of()
                    .customMessage("로그인 실패")
                    .request(new AuthFailureMessage(myUser))
                    .build();
        } else if (myUser.isDisabled()) {
            throw CustomAccessDenied.of()
                    .systemMessage("시스템")
                    .customMessage("로그인 실패")
                    .request(new AuthFailureMessage(myUser))
                    .build();
        }
    }*/
}
