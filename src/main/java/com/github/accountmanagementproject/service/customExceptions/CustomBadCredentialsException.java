package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomBadCredentialsException extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public CustomBadCredentialsException(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public CustomBadCredentialsException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }
}
