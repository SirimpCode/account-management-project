package com.github.accountmanagementproject.common.exceptions;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends MakeRuntimeException{


    protected CustomBadRequestException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }
    public static of of() {
        return new of();
    }

    public static class of extends MakeRuntimeException.ExceptionBuilder<of, CustomBadRequestException>{
        public of() {
            super(CustomBadRequestException.class);
        }
        @Override
        protected of self() {
            return this;
        }
    }

}
