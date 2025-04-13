package com.github.accountmanagementproject.web.dto.account;

import com.github.accountmanagementproject.repository.account.users.enums.Gender;

public interface AccountDefaultValueInterface {
    default String getDefaultProfileImg(Gender gender){
        if(gender==null) return Gender.UNKNOWN.getDefaultProfileImgUrl();
        return gender.getDefaultProfileImgUrl();
    }
}
