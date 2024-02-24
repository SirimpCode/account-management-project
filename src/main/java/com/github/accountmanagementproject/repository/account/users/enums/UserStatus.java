package com.github.accountmanagementproject.repository.account.users.enums;

import lombok.Getter;

@Getter
public enum UserStatus implements MyEnumInterface{
    NORMAL,
    LOCK,
    TEMP,
    WITHDRAWAL;

    @Override
    public String getValue() {
        return null;
    }
}