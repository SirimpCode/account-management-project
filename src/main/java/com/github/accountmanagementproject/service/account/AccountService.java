package com.github.accountmanagementproject.service.account;

import com.github.accountmanagementproject.common.AccountServiceModule;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.common.converter.mapper.UserMapper;
import com.github.accountmanagementproject.web.dto.account.auth.response.MyInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountServiceModule accountServiceModule;

    public MyInfoResponse myInfoByEmail(String principal) {
        MyUser myUser = accountServiceModule.findMyUserFetchJoin(principal);
        return UserMapper.INSTANCE.myUserToAccountDto(myUser);
    }


}
