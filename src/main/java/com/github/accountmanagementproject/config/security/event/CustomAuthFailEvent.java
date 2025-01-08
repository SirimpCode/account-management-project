package com.github.accountmanagementproject.config.security.event;

import com.github.accountmanagementproject.service.exceptions.CustomBadCredentialsEventEx;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

@Getter
public class CustomAuthFailEvent extends ApplicationEvent {

    private final CustomBadCredentialsEventEx exception;

    public CustomAuthFailEvent(Authentication authentication, CustomBadCredentialsEventEx ex) {
        super(authentication);
        this.exception = ex;
    }

}
