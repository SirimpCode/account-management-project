package com.github.accountmanagementproject.service.customExceptions;



public class AccountLockedException extends MakeRuntimeException{


    protected AccountLockedException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }

    public static class ExceptionBuilder extends MakeRuntimeException.ExceptionBuilder<ExceptionBuilder, AccountLockedException>{
        public ExceptionBuilder() {
            super(AccountLockedException.class);
        }
        @Override
        protected ExceptionBuilder self() {
            return this;
        }
    }

}
