package com.github.accountmanagementproject.service.customExceptions;

import lombok.Getter;

@Getter
public abstract class MakeRuntimeException extends RuntimeException {
    private final String customMessage;
    private final Object request;

    // 예외 타입을 제네릭으로 받음
    protected MakeRuntimeException(ExceptionBuilder<?, ?> exceptionBuilder) {
        super(exceptionBuilder.systemMessage);
        this.customMessage = exceptionBuilder.customMessage;
        this.request = exceptionBuilder.request;
    }

    // 제네릭 T: 예외 빌더 타입, E: 예외 클래스 타입
    public abstract static class ExceptionBuilder<T extends ExceptionBuilder<T, E>, E extends MakeRuntimeException> {
        private String systemMessage;
        private String customMessage;
        private Object request;
        private final Class<E> exceptionClass;

        protected ExceptionBuilder(Class<E> exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

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

        //ExceptionBuilder 인스턴스를 사용하여 특정 예외 클래스의 새로운 인스턴스를 생성
        public E build(){
            try {
                return exceptionClass.getDeclaredConstructor(ExceptionBuilder.class).newInstance(this);
            } catch (Exception e) {
                throw new CustomServerException.ExceptionBuilder().customMessage("Failed to create exception instance")
                        .systemMessage(e.getMessage())
                        .build();
            }
        };
    }
}
