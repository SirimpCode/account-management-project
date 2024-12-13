package com.github.accountmanagementproject.repository.account.users.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.accountmanagementproject.service.mappers.converter.MyConverter;

public interface MyEnumInterface {
    //getValue 의 구현이 database 에 저장됨. 불러올때도 사용
    @JsonValue
    String getValue();
}
