package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;

public class UserStatusConverter extends MyConverter<UserStatus> {
    public UserStatusConverter() {
        super(UserStatus.class);
    }
}
