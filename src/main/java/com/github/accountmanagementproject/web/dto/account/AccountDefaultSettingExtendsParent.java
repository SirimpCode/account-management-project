package com.github.accountmanagementproject.web.dto.account;

import com.github.accountmanagementproject.repository.account.users.enums.Gender;


public abstract class AccountDefaultSettingExtendsParent extends AccountParent {

    public void setDefaultProfileImage() {
        if (getProfileImg() == null) {
            if (super.getGender() == null || super.getGender() == Gender.UNKNOWN)
                setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/anonymous-user-icon.png");
            else if (super.getGender() == Gender.MALE)
                setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/man-user-color-icon.png");
            else
                setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/woman-user-color-icon.png");
        }
    }
}
