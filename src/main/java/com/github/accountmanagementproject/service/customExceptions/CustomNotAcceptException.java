package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomNotAcceptException extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public CustomNotAcceptException(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public CustomNotAcceptException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }
}
