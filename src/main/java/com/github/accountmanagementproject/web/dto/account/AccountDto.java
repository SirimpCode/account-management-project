package com.github.accountmanagementproject.web.dto.account;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirm;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private String dateOfBirth;
    private String profileImg;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastLogin;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<String> roles;
}
