package com.github.accountmanagementproject.repository.account.users.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum UserStatus implements MyEnumInterface{
    NORMAL("정상 계정"),
    LOCK("잠긴 계정"),
    TEMP("임시 계정"),
    WITHDRAWAL("탈퇴 계정");

    private final String description;


    @Override
    @JsonValue//outValue
    public String getValue() {
        return this.description;
    }

}