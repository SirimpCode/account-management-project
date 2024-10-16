package com.github.accountmanagementproject.web.advice;

import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Hidden
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //찾을 수 없는 요청
    public CustomErrorResponse handleNotFoundException(CustomNotFoundException messageAndRequest) {
        return makeResponse(HttpStatus.NOT_FOUND, messageAndRequest);
    }
    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //잘못된 요청
    public CustomErrorResponse handleBadRequestException(CustomBadRequestException messageAndRequest) {
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

    @ExceptionHandler(CustomServerException.class) // 서버에러지만 예외처리가 필요할때
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleCustomServerException(CustomServerException messageAndRequest) {
        return makeResponse(HttpStatus.INTERNAL_SERVER_ERROR, messageAndRequest);
    }

    @ExceptionHandler(NotFoundSocialAccount.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public CustomErrorResponse handleNotFoundSocialAccount(NotFoundSocialAccount messageAndRequest) {
        return makeResponse(HttpStatus.UNPROCESSABLE_ENTITY, messageAndRequest);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})//Valid 익셉션 처리
    public CustomErrorResponse handleValidException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException validException) {
            FieldError fieldError = validException.getBindingResult().getFieldError();
            String fieldName = fieldError != null ? fieldError.getField() : "Unknown field";
            Object fieldValue = fieldError != null ? fieldError.getRejectedValue() : "Unknown Value";
            String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";

            return handleBadRequestException(new CustomBadRequestException.ExceptionBuilder()
                    .customMessage(errorMessage)
                    .systemMessage("유효성 검사 실패")
                    .request(fieldName+" : "+fieldValue)
                    .build());

        } else if (ex instanceof MethodArgumentTypeMismatchException validException){

            return handleBadRequestException(new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("잘못된 타입 전달")
                    .systemMessage(validException.getMessage())
                    .request(validException.getName() +"="+validException.getValue())
                    .build());
        }else return handleBadRequestException(new CustomBadRequestException.ExceptionBuilder()
                .customMessage("잘못된 요청")
                .systemMessage(ex.getMessage())
                .build());
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

