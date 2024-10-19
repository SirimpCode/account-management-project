package com.github.accountmanagementproject.repository.account.users.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RolesEnum implements MyEnumInterface{
    ROLE_ADMIN("운영자"),
    ROLE_USER("유저");

    private final String kor;



    @Override
    @JsonValue//제이슨으로 직렬화할때의 값
    public String getValue() {
        return this.kor;
    }


}
