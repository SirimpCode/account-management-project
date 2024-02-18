package com.github.accountmanagementproject.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CustomErrorResponse {
    private ErrorDetail error;

    @Getter
    @Builder
    public static class ErrorDetail {
        private int code;
        private HttpStatus httpStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String systemMessage;
        private String customMessage;
        private Object request;
        private LocalDateTime timestamp;
    }
}
