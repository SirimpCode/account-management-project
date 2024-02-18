package com.github.accountmanagementproject.repository.account.users.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL,
    LOCK,
    TEMP,
    WITHDRAWAL;
}