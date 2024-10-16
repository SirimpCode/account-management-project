package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends MakeRuntimeException{


    protected CustomBadRequestException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomBadRequestException>{
        public ExceptionBuilder() {
            super(CustomBadRequestException.class);
        }
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}
