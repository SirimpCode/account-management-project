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
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.NOT_FOUND, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }
    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //잘못된 요청
    public CustomErrorResponse handleNotFoundException(CustomBadRequestException messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.BAD_REQUEST, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //키 중복
    public CustomErrorResponse handleDuplicateKeyException(DuplicateKeyException messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.CONFLICT, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.LOCKED) //잠긴 계정
    public CustomErrorResponse handleAccountLockedException(AccountLockedException messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.LOCKED, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //인증 오류
    public CustomErrorResponse handleBadCredentialsException(CustomBadCredentialsException messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.UNAUTHORIZED, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }

    @ExceptionHandler(CustomAccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //인가 오류
    public CustomErrorResponse handleNotAccessDenied(CustomAccessDenied messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.FORBIDDEN, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }



    @ExceptionHandler(CustomNotAcceptException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) //처리할 수 없는 요청
    public CustomErrorResponse handleNotAcceptException(CustomNotAcceptException messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.NOT_ACCEPTABLE, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }



    @ExceptionHandler(CustomBindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //요청이 올바르지만 처리할 수 없음
    public CustomErrorResponse handleCustomBindException(CustomBindException messageAndRequest) {
        return new CustomErrorResponse(new CustomErrorResponse.ErrorDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, messageAndRequest.getMessage(),messageAndRequest.getCustomMessage(), messageAndRequest.getRequest()
        ));
    }
}

