package com.github.accountmanagementproject.web.advice;
import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //찾을 수 없는 요청
    public CustomErrorResponse handleNotFoundException(CustomNotFoundException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.NOT_FOUND)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }
    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //잘못된 요청
    public CustomErrorResponse handleNotFoundException(CustomBadRequestException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //키 중복
    public CustomErrorResponse handleDuplicateKeyException(DuplicateKeyException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.CONFLICT)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.LOCKED) //잠긴 계정
    public CustomErrorResponse handleAccountLockedException(AccountLockedException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.LOCKED)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //인증 오류
    public CustomErrorResponse handleBadCredentialsException(CustomBadCredentialsException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }

    @ExceptionHandler(CustomAccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //인가 오류
    public CustomErrorResponse handleNotAccessDenied(CustomAccessDenied messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.FORBIDDEN)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }



    @ExceptionHandler(CustomNotAcceptException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) //처리할 수 없는 요청
    public CustomErrorResponse handleNotAcceptException(CustomNotAcceptException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }



    @ExceptionHandler(CustomBindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //요청이 올바르지만 처리할 수 없음
    public CustomErrorResponse handleCustomBindException(CustomBindException messageAndRequest) {
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                .systemMessage(messageAndRequest.getMessage())
                .customMessage(messageAndRequest.getCustomMessage())
                .request(messageAndRequest.getRequest())
                .build();
    }
}

