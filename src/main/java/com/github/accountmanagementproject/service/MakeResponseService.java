package com.github.accountmanagementproject.service;

import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MakeResponseService {
    public CustomSuccessResponse.SuccessDetail makeSuccessDetail(HttpStatus httpStatus, String message, Object... data){
        return CustomSuccessResponse.SuccessDetail.builder()
                .code(httpStatus.value())
                .httpStatus(httpStatus)
                .message(message)
                .responseData(data.length==1?data[0]:data)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
