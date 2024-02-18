package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomAccessDenied extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public CustomAccessDenied(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public CustomAccessDenied(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }
}
