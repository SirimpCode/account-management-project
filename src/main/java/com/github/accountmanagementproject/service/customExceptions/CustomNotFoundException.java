package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public CustomNotFoundException(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public CustomNotFoundException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }
}
