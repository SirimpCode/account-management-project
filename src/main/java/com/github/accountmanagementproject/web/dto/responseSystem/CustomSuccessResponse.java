package com.github.accountmanagementproject.web.dto.responseSystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class CustomSuccessResponse {
    private SuccessDetail success;
    @Getter
    public static class SuccessDetail {
        private int code;
        private HttpStatus httpStatus;
        private String message;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private Object responseData;
        private LocalDateTime timestamp;

        public SuccessDetail(){
            this.httpStatus = HttpStatus.OK;
        }

        public SuccessDetail httpStatus(HttpStatus httpStatus){
            this.httpStatus = httpStatus;
            return this;
        }
        public SuccessDetail message(String message){
            this.message = message;
            return this;
        }
        public SuccessDetail responseData(Object data){
            this.responseData = data;
            return this;
        }
        public CustomSuccessResponse build(){
            this.code = httpStatus.value();
            this.timestamp = LocalDateTime.now();
            return new CustomSuccessResponse(this);
        }
    }


}
