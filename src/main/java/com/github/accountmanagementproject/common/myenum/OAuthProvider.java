package com.github.accountmanagementproject.common.myenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OAuthProvider implements MyEnumInterface{
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글"),
    GITHUB("깃허브"),
    FACEBOOK("페이스북"),
    MICROSOFT("마이크로소프트"),
    TWITTER("트위터");
    private final String value;
}
