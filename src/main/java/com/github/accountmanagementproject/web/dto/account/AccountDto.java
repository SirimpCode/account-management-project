package com.github.accountmanagementproject.web.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String passwordConfirm;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String dateOfBirth;
    private String profileImg;
    private Set<String> roles;
}
