package com.github.accountmanagementproject.web.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SignUpOrLoginResponse {
    private String nickname;
    private LocalDateTime lastLogin;
}
