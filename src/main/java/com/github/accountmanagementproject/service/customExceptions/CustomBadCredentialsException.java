package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomBadCredentialsException extends MakeRuntimeException{

    protected CustomBadCredentialsException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomBadCredentialsException>{
        public ExceptionBuilder() {
            super(CustomBadCredentialsException.class);
        }
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }


}
