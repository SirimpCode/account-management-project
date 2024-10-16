package com.github.accountmanagementproject.repository.account.users.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OAuthProvider implements MyEnumInterface{
    KAKAO("카카오"), NAVER("네이버"), GOOGLE("구글");

    private final String description;

    @Override
    @JsonValue//이건 응답으로 직렬화 할때 사용될 값
    //db에 저장될 값
    public String getValue() {
        return this.description;
    }
}
