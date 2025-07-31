package com.github.accountmanagementproject.common.exceptions;

import lombok.Getter;

@Getter
public class CustomBadCredentialsException extends MakeRuntimeException{


    protected CustomBadCredentialsException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static ExceptionBuilder of() {
        return new ExceptionBuilder();
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomBadCredentialsException>{

        protected ExceptionBuilder() {
            super(CustomBadCredentialsException.class);
        }

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }


}
