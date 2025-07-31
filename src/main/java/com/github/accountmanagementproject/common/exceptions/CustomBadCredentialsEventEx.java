package com.github.accountmanagementproject.common.exceptions;

import com.github.accountmanagementproject.service.account.auth.userdetails.CustomUserDetails;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;

@Getter
public class CustomBadCredentialsEventEx extends BadCredentialsException {
    private final CustomUserDetails user;
    private CustomBadCredentialsEventEx (String message, CustomUserDetails user){
        super(message);
        this.user = user;
    }

    public static CustomBadCredentialsEventEx of(String message, CustomUserDetails user) {
        return new CustomBadCredentialsEventEx(message, user);
    }
}
