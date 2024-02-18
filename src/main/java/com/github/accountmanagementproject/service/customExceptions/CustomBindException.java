package com.github.accountmanagementproject.service.customExceptions;


import lombok.Getter;

@Getter
public class CustomBindException extends RuntimeException{
    private final String customMessage;
    private final Object request;

    public CustomBindException(String systemMessage,String customMessage, Object request) {
        super(systemMessage);
        this.customMessage = customMessage;
        this.request = request;
    }
    public CustomBindException(String customMessage, Object request){
        this.customMessage = customMessage;
        this.request = request;
    }

}
