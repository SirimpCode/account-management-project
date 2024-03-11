package com.github.accountmanagementproject.web.dto.account;


import ch.qos.logback.core.pattern.SpacePadder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String emailOrPhoneNumber;
    private String password;

    public Authentication toAuthentication(){
        return new UsernamePasswordAuthenticationToken(emailOrPhoneNumber, password);
    }
}
