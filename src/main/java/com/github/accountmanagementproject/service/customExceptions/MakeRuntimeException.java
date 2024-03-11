package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public abstract class MakeRuntimeException extends RuntimeException {
    private final String customMessage;
    private final Object request;

    //모든 종류의 제네릭을 처리할 수 있도록 <?>사용 하위클래스에서 이용하므로 protected 선언
    protected MakeRuntimeException(ExceptionBuilder<?> exceptionBuilder) {
        super(exceptionBuilder.systemMessage);
        this.customMessage = exceptionBuilder.customMessage;
        this.request = exceptionBuilder.request;
    }

    public abstract static class ExceptionBuilder<T extends ExceptionBuilder<T>> {
        private String systemMessage;
        private String customMessage;
        private Object request;

        public T systemMessage(String systemMessage) {
            this.systemMessage = systemMessage;
            return self();
        }

        public T customMessage(String customMessage) {
            this.customMessage = customMessage;
            return self();
        }

        public T request(Object request) {
            this.request = request;
            return self();
        }

        protected abstract T self();

        public abstract MakeRuntimeException build();
    }
}
