package com.github.accountmanagementproject.web.dto.accountAuth.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialAccountDto {
    private Long socialId;
    private String provider;
    private String nickName;
    private String email;
    private String imageUrl;
    private String gender;
    private String dateOfBirth;
}
