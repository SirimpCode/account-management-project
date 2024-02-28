package com.github.accountmanagementproject.repository.account.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.accountmanagementproject.service.mappers.converter.MyConverter;
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
    @JsonCreator
    public static Gender inValue(String gender){
        return MyConverter.EnumValueToEnum(gender, Gender.class);
    }
    @JsonValue
    public String outValue(){
        return this.value;
    }

}
