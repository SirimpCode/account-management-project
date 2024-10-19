package com.github.accountmanagementproject.repository.account.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.accountmanagementproject.service.mappers.converter.GenderConverter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender implements MyEnumInterface {
    MALE("남성"),
    FEMALE("여성"),
    UNKNOWN("미정");

    private final String value;
/*
* 그리고 inValue는 남성 or 여성 등 value 값으로 들어올때 enum으로 변환해주기 위해서
* JsonValue는 이 이넘클래스를 직렬화할때 value값을 반환하기 위해서*/

    @JsonCreator
    public static Gender inValue(String gender){
        return new GenderConverter().convertToEntityAttribute(gender);
    }

    @Override
    @JsonValue
    public String getValue(){
        return this.value;
    }

}
