package com.github.accountmanagementproject.service.exceptions;

public class CustomServerException  extends MakeRuntimeException{

    protected CustomServerException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, CustomServerException>{
        public ExceptionBuilder() {
            super(CustomServerException.class);
        }
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}