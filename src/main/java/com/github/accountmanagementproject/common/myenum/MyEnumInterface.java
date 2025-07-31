package com.github.accountmanagementproject.common.myenum;

import com.fasterxml.jackson.annotation.JsonValue;

public interface MyEnumInterface {
    //getValue 의 구현이 database 에 저장됨. 불러올때도 사용
    @JsonValue
    String getValue();
}
