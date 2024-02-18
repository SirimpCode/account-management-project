package com.github.accountmanagementproject.web.dto.account;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String emailOrPhoneNumber;
    private String password;
}
