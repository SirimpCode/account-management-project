package com.github.accountmanagementproject.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomErrorResponse<T> {
    private ErrorDetail<T> error;

    public static <T> ErrorDetail<T> builder(){
        return new ErrorDetail<>();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ErrorDetail<T> {
        private int code;
        private HttpStatus httpStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String systemMessage;
        private String customMessage;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private T request;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp;

        public ErrorDetail<T> httpStatus(HttpStatus httpStatus){
            this.httpStatus = httpStatus;
            return this;
        }
        public ErrorDetail<T> systemMessage(String systemMessage){
            this.systemMessage = systemMessage;
            return this;
        }
        public ErrorDetail<T> customMessage(String customMessage){
            this.customMessage = customMessage;
            return this;
        }
        public ErrorDetail<T> request(T request){
            this.request = request;
            return this;
        }

        public CustomErrorResponse<T> build(){
            this.code = httpStatus.value();
            this.timestamp = LocalDateTime.now();
            return new CustomErrorResponse<>(this);
        }

    }
}
