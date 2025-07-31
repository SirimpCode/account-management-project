package com.github.accountmanagementproject.common.exceptions;

import lombok.Getter;

@Getter
public class CustomAccessDenied extends MakeRuntimeException{


    protected CustomAccessDenied(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static of of() {
        return new of();
    }

    public static class of extends MakeRuntimeException.ExceptionBuilder<of, CustomAccessDenied>{
        public of() {
            super(CustomAccessDenied.class);
        }
        @Override
        protected of self() {
            return this;
        }
    }

}
