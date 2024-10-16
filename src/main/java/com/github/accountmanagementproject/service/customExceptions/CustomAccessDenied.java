package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomAccessDenied extends MakeRuntimeException{


    protected CustomAccessDenied(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomAccessDenied>{
        public ExceptionBuilder() {
            super(CustomAccessDenied.class);
        }
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}
