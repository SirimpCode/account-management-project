package com.github.accountmanagementproject.service.exceptions;



public class AccountLockedException extends MakeRuntimeException{


    protected AccountLockedException(MakeRuntimeException.ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder);
    }
    public static of of() {
        return new of();
    }

    public static class of extends MakeRuntimeException.ExceptionBuilder<of, AccountLockedException>{
        public of() {
            super(AccountLockedException.class);
        }
        @Override
        protected of self() {
            return this;
        }
    }

}
