package com.github.accountmanagementproject.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CustomSuccessResponse {
    private SuccessDetail success;
    @Getter
    @Builder
    public static class SuccessDetail {
        private int code;
        private
        HttpStatus httpStatus;
        private String message;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private Object responseData;
        private LocalDateTime timestamp;
    }

}
