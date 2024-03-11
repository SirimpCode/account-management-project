package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public class CustomNotAcceptException extends MakeRuntimeException{

    private CustomNotAcceptException(ExceptionBuilder exceptionBuilder) {
        super(exceptionBuilder);
    }


    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder>{

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
        @Override
        public MakeRuntimeException build() {
            return new CustomNotAcceptException(this);
        }
    }

}
