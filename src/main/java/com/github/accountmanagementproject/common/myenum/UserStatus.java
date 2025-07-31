package com.github.accountmanagementproject.common.myenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus implements MyEnumInterface{
    NORMAL("정상 계정"),
    LOCK("잠긴 계정"),
    TEMP("임시 계정"),
    WITHDRAWAL("탈퇴 계정");

    private final String value;
}