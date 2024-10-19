package com.github.accountmanagementproject.web.advice;

import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.web.dto.responseSystem.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

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

    @ExceptionHandler(DataIntegrityViolationException.class)//데이터 무결성 위반
    public CustomErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> duplicateInfo = getDuplicateKeyAndValue(ex.getMessage());
        if (duplicateInfo != null) {
            return handleDuplicateKeyException(
                    new DuplicateKeyException.ExceptionBuilder()
                            .systemMessage(ex.getMessage())
                            .customMessage(duplicateInfo.get("key") + " 중복")
                            .request(duplicateInfo)
                            .build()
            );
        } else {
            return handleCustomServerException(
                    new CustomServerException.ExceptionBuilder()
                            .systemMessage(ex.getMessage())
                            .customMessage("데이터 무결성 위반")
                            .build()
            );
        }
    }

    private Map<String, String> getDuplicateKeyAndValue(String message) {
        Matcher keyMatcher = Pattern.compile("for key '(.+?)'").matcher(message);
        Matcher valueMatcher = Pattern.compile("Duplicate entry '(.+?)'").matcher(message);

        if (keyMatcher.find() & valueMatcher.find()) {
            return Map.of("key",convertToCamelcase(keyMatcher.group(1)),
                    "value",valueMatcher.group(1));
        } else {
            return null;
        }
    }
    private String convertToCamelcase(String snakeCase){
        String[] words = snakeCase.split("_");
        return IntStream.range(0, words.length)
                .mapToObj(i -> i == 0 ? words[i].toLowerCase() : words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase())
                .collect(Collectors.joining());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class
    }) // Valid 익셉션 처리
    public CustomErrorResponse handleValidException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException validException) {
            return handleBadRequestException(notValidToBadRequestException(validException));
        } else if (ex instanceof MethodArgumentTypeMismatchException validException) {
            return handleBadRequestException(typeMismatchToBadRequestException(validException));
        } else if (ex instanceof ConstraintViolationException validException) {
            return handleBadRequestException(constraintViolationToBadRequestException(validException));
        } else {
            return handleBadRequestException(genericExToBadRequestException(ex));
        }
    }


    private CustomErrorResponse makeResponse(HttpStatus httpStatus, MakeRuntimeException exception){
        return new CustomErrorResponse.ErrorDetail()
                .httpStatus(httpStatus)
                .systemMessage(exception.getMessage())
                .customMessage(exception.getCustomMessage())
                .request(exception.getRequest())
                .build();
    }

    private CustomBadRequestException notValidToBadRequestException(MethodArgumentNotValidException validException){

        FieldError fieldError = validException.getBindingResult().getFieldError();
        String fieldName = fieldError != null ? fieldError.getField() : "Unknown field";
        Object fieldValue = fieldError != null ? fieldError.getRejectedValue() : "Unknown Value";
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";

        return new CustomBadRequestException.ExceptionBuilder()
                .customMessage(errorMessage)
                .systemMessage("유효성 검사 실패")
                .request(fieldName+" : "+fieldValue)
                .build();
    }
    private CustomBadRequestException typeMismatchToBadRequestException(MethodArgumentTypeMismatchException validException){
        return new CustomBadRequestException.ExceptionBuilder()
                .customMessage("잘못된 타입 전달")
                .systemMessage(validException.getMessage())
                .request(validException.getName() +"="+validException.getValue())
                .build();
    }



    private CustomBadRequestException genericExToBadRequestException(Exception ex) {
        return new CustomBadRequestException.ExceptionBuilder()
                .customMessage("잘못된 요청")
                .systemMessage(ex.getMessage())
                .build();
    }
    private CustomBadRequestException constraintViolationToBadRequestException(ConstraintViolationException ex) {
        String message = extractConstraintViolationMessage(ex);
        String request = extractRequestFieldAndValue(ex);

        return new CustomBadRequestException.ExceptionBuilder()
                .customMessage("잘못된 요청")
                .systemMessage(message)
                .request(request)
                .build();
    }
    //ConstraintViolationException 의 값 추출을 위한
    private String extractConstraintViolationMessage(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst().orElse("Unknown message");
    }

    private String extractRequestFieldAndValue(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream().map(constraintViolation -> {
            String fieldName = extractLeafNodeName(constraintViolation.getPropertyPath());
            Object invalidValue = constraintViolation.getInvalidValue();
            return fieldName + "=" + invalidValue;
        }).findFirst().orElse("Unknown value");
    }

    private String extractLeafNodeName(Path propertyPath) {
        return StreamSupport.stream(propertyPath.spliterator(), false)
                .reduce((first, second) -> second)
                .map(Path.Node::getName)
                .orElse("Unknown field");
    }



}

