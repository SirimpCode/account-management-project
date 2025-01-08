package com.github.accountmanagementproject.service.exceptions;

import lombok.Getter;

@Getter
public class NotFoundSocialAccount extends MakeRuntimeException{


    protected NotFoundSocialAccount(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }
    public static ExceptionBuilder of() {
        return new ExceptionBuilder();
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, NotFoundSocialAccount>{

        public ExceptionBuilder() {
            super(NotFoundSocialAccount.class);
        }

        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }
}
