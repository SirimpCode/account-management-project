package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomNotAcceptException extends MakeRuntimeException{


    protected CustomNotAcceptException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomNotAcceptException>{

        protected ExceptionBuilder() {
            super(CustomNotAcceptException.class);
        }

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}
