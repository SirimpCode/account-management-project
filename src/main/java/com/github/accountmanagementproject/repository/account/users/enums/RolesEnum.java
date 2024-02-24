package com.github.accountmanagementproject.repository.account.users.enums;

import lombok.Getter;

@Getter
public enum RolesEnum implements MyEnumInterface{
    ROLE_ADMIN("운영자"),
    ROLE_USER("유저");

    private final String kor;

    RolesEnum(String value){
        this.kor = value;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
