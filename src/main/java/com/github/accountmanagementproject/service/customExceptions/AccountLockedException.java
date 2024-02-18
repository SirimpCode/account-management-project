package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class AccountLockedException extends RuntimeException{
    private final String customMessage;
    private final Object request;
    public AccountLockedException(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public AccountLockedException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }

}
