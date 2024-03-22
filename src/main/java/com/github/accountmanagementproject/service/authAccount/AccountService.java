package com.github.accountmanagementproject.service.authAccount;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomNotFoundException;
import com.github.accountmanagementproject.service.mappers.UserMapper;
import com.github.accountmanagementproject.web.dto.accountAuth.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final MyUsersJpa myUsersJpa;
    public AccountDto myInfoByEmail(String principal) {
        MyUser myUser = myUsersJpa.findByEmailJoin(principal).orElseThrow(()->
                new CustomNotFoundException.ExceptionBuilder()
                        .customMessage("해당 토큰 정보의 계정이 존재하지 않습니다.")
                        .request(principal)
                        .build());
        AccountDto accountDto = UserMapper.INSTANCE.myUserToAccountDto(myUser);
        return UserMapper.INSTANCE.myUserToAccountDto(myUser);

    }
}
