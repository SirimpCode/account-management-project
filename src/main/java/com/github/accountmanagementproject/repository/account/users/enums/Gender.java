package com.github.accountmanagementproject.repository.account.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender implements MyEnumInterface {
    MALE("남성","https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/man-user-color-icon.png"),
    FEMALE("여성","https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/woman-user-color-icon.png"),
    UNKNOWN("미정","https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/anonymous-user-icon.png");
    private final String value;
    private final String defaultProfileImgUrl;

    // Enum의 기본 이미지에 현재 이미지가 포함되는지 검사
    public static boolean isDefaultProfileImg(String currentImg) {
        for (Gender gender : values()) {
            if (gender.getDefaultProfileImgUrl().equals(currentImg)) return true;
        }
        return false;
    }

}
