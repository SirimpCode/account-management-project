package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomAccessDenied extends MakeRuntimeException{

    private CustomAccessDenied(ExceptionBuilder exceptionBuilder) {
        super(exceptionBuilder);
    }
    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder>{
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
        @Override
        public MakeRuntimeException build() {
            return new CustomAccessDenied(this);
        }
    }

}
