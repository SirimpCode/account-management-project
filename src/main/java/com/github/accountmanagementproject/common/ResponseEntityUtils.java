package com.github.accountmanagementproject.common;


import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    public static <T> ResponseEntity<CustomSuccessResponse<T>> createResponseEntity(CustomSuccessResponse<T> body, ResponseCookie cookie) {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(body.getHttpStatus());
        if (cookie != null && !cookie.getValue().isEmpty()) {
            responseBuilder.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return responseBuilder.body(body);
    }

    public static <T> ResponseEntity<CustomSuccessResponse<T>> createResponseEntity(CustomSuccessResponse<T> body) {
        return ResponseEntity.status(body.getHttpStatus()).body(body);
    }


}
