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
/*오버라이드한 getValue가 없는 이유는 필드값 value의 대한 getter를 어노테이션으로 작성해놨기 때문
* 그리고 inValue는 남성 or 여성 등 value 값으로 들어올때 enum으로 변환해주기 위해서
* JsonValue는 이 이넘클래스를 직렬화할때 value값을 반환하기 위해서*/
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
