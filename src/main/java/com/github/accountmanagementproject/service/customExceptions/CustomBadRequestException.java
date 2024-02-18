package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public CustomBadRequestException(String systemMessage, String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public CustomBadRequestException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }
}
