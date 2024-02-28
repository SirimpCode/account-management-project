package com.github.accountmanagementproject.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CustomErrorResponse {
    private ErrorDetail error;

    @Getter
    public static class ErrorDetail {
        private int code;
        private HttpStatus httpStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String systemMessage;
        private String customMessage;
        private Object request;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp;


        public ErrorDetail httpStatus(HttpStatus httpStatus){
            this.httpStatus = httpStatus;
            return this;
        }
        public ErrorDetail systemMessage(String systemMessage){
            this.systemMessage = systemMessage;
            return this;
        }
        public ErrorDetail customMessage(String customMessage){
            this.customMessage = customMessage;
            return this;
        }
        public ErrorDetail request(Object request){
            this.request = request;
            return this;
        }

        public CustomErrorResponse build(){
            this.code = httpStatus.value();
            this.timestamp = LocalDateTime.now();
            return new CustomErrorResponse(this);
        }

    }
}
