package com.github.accountmanagementproject.web.dto.response;

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
        private final int code;
        private final HttpStatus httpStatus;
        private final String message;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private final Object responseData;
        private final LocalDateTime timestamp;
        public SuccessDetail(HttpStatus httpStatus, String message, Object... data){
            this.code = httpStatus.value();
            this.httpStatus = httpStatus;
            this.message = message;
            this.responseData = data.length==1?data[0]:data;
            this.timestamp = LocalDateTime.now();
        }
    }


}
