package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;

public class UserStatusConverter extends MyConverter<UserStatus> {
    public static final Class<UserStatus> ENUM_CLASS = UserStatus.class;
    public UserStatusConverter() {
        super(ENUM_CLASS);
    }
}
