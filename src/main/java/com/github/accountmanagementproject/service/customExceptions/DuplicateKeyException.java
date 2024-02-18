package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class DuplicateKeyException extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public DuplicateKeyException(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public DuplicateKeyException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }
}
