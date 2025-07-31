package com.github.accountmanagementproject.common.converter.custom;

import com.github.accountmanagementproject.common.myenum.UserStatus;

public class UserStatusConverter extends MyConverter<UserStatus> {
    public UserStatusConverter() {
        super(UserStatus.class);
    }
}
