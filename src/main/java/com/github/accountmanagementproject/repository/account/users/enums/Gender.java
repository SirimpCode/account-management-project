package com.github.accountmanagementproject.repository.account.users.enums;

import lombok.Getter;

@Getter
public enum Gender implements MyEnumInterface {
    MALE("남성"),
    FEMALE("여성"),
    UNKNOWN("미정");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

}
