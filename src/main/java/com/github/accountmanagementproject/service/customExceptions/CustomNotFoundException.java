package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends MakeRuntimeException{

    private CustomNotFoundException(ExceptionBuilder exceptionBuilder) {
        super(exceptionBuilder);
    }


    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder>{

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
        @Override
        public MakeRuntimeException build() {
            return new CustomNotFoundException(this);
        }
    }

}
