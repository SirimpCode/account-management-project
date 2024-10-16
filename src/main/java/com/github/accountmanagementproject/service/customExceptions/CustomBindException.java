package com.github.accountmanagementproject.service.customExceptions;


import lombok.Getter;

@Getter
public class CustomBindException extends MakeRuntimeException{


    protected CustomBindException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomBindException>{
        public ExceptionBuilder() {
            super(CustomBindException.class);
        }
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}
