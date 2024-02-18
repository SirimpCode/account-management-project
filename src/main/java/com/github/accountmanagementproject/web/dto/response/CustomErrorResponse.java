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
        private final int code;
        private final HttpStatus httpStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String systemMessage;
        private final String customMessage;
        private final Object request;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime timestamp;

        public ErrorDetail(HttpStatus httpStatus, String systemMessage, String customMessage, Object request){
            this.code = httpStatus.value();
            this.httpStatus = httpStatus;
            this.systemMessage = systemMessage;
            this.customMessage = customMessage;
            this.request = request;
            this.timestamp = LocalDateTime.now();
        }
    }
}
