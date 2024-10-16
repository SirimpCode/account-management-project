package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends MakeRuntimeException{


    protected CustomNotFoundException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomNotFoundException> {

        public ExceptionBuilder() {
            super(CustomNotFoundException.class);
        }

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}
