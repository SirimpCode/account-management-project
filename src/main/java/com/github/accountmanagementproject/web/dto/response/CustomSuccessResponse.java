package com.github.accountmanagementproject.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomSuccessResponse<T> {
    private SuccessDetail<T> success;


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class SuccessDetail<T> {
        private  int code;
        private  HttpStatus httpStatus;
        private  String message;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private  T responseData;
        private  LocalDateTime timestamp;



        public static <T> SuccessDetail<T> of(HttpStatus httpStatus, String message, T data) {
            return new SuccessDetail<>(httpStatus.value(), httpStatus, message, data, LocalDateTime.now());
        }
    }
    public HttpStatus getHttpStatus(){
        return this.success.getHttpStatus();
    }

    public static <T> CustomSuccessResponse<T> of(HttpStatus httpStatus, String message, T data){
        return new CustomSuccessResponse<>(
                SuccessDetail.of(httpStatus, message, data)
        );
    }
    public static <T> CustomSuccessResponse<T> ofOk(String message, T data){
        return new CustomSuccessResponse<>(
                SuccessDetail.of(HttpStatus.OK, message, data)
        );
    }
    public static <T> CustomSuccessResponse<T> emptyData(HttpStatus httpStatus, String message){
        return new CustomSuccessResponse<>(
                SuccessDetail.of(httpStatus, message, null)
        );
    }
    public static <T> CustomSuccessResponse<T> emptyDataOk(String message){
        return new CustomSuccessResponse<>(
                SuccessDetail.of(HttpStatus.OK, message, null)
        );
    }

}




        /* 빌더패턴 필요할때 사용
        public SuccessDetail<T> httpStatus(HttpStatus httpStatus){
            this.httpStatus = httpStatus;
            return this;
        }
        public SuccessDetail<T> message(String message){
            this.message = message;
            return this;
        }
        public SuccessDetail<T> responseData(T data){
            this.responseData = data;
            return this;
        }
        public CustomSuccessResponse build(){
            this.code = httpStatus.value();
            this.timestamp = LocalDateTime.now();
            return new CustomSuccessResponse(this);
        }
           */
