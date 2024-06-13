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
        return makeResponse(HttpStatus.NOT_FOUND, messageAndRequest);
    }
    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //잘못된 요청
    public CustomErrorResponse handleNotFoundException(CustomBadRequestException messageAndRequest) {
        return makeResponse(HttpStatus.BAD_REQUEST, messageAndRequest);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //키 중복
    public CustomErrorResponse handleDuplicateKeyException(DuplicateKeyException messageAndRequest) {
        return makeResponse(HttpStatus.CONFLICT, messageAndRequest);
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.LOCKED) //잠긴 계정
    public CustomErrorResponse handleAccountLockedException(AccountLockedException messageAndRequest) {
        return makeResponse(HttpStatus.LOCKED, messageAndRequest);
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //인증 오류
    public CustomErrorResponse handleBadCredentialsException(CustomBadCredentialsException messageAndRequest) {
        return makeResponse(HttpStatus.UNAUTHORIZED, messageAndRequest);
    }

    @ExceptionHandler(CustomAccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //인가 오류
    public CustomErrorResponse handleNotAccessDenied(CustomAccessDenied messageAndRequest) {
        return makeResponse(HttpStatus.FORBIDDEN, messageAndRequest);
    }

    @ExceptionHandler(CustomNotAcceptException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) //처리할 수 없는 요청
    public CustomErrorResponse handleNotAcceptException(CustomNotAcceptException messageAndRequest) {
        return makeResponse(HttpStatus.NOT_ACCEPTABLE, messageAndRequest);
    }

    @ExceptionHandler(CustomBindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //요청이 올바르지만 처리할 수 없음
    public CustomErrorResponse handleCustomBindException(CustomBindException messageAndRequest) {
        return makeResponse(HttpStatus.UNPROCESSABLE_ENTITY, messageAndRequest);
    }

    @ExceptionHandler(CustomServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleCustomServerException(CustomServerException messageAndRequest) {
        return makeResponse(HttpStatus.INTERNAL_SERVER_ERROR, messageAndRequest);
    }

    private CustomErrorResponse makeResponse(HttpStatus httpStatus, MakeRuntimeException exception){
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(httpStatus)
                .systemMessage(exception.getMessage())
                .customMessage(exception.getCustomMessage())
                .request(exception.getRequest())
                .build();
    }
}

