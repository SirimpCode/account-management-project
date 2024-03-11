package com.github.accountmanagementproject.service.customExceptions;


import lombok.Getter;

@Getter
public class CustomBindException extends MakeRuntimeException{

    private CustomBindException(ExceptionBuilder exceptionBuilder) {
        super(exceptionBuilder);
    }


    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder>{

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
        @Override
        public MakeRuntimeException build() {
            return new CustomBindException(this);
        }
    }

}
