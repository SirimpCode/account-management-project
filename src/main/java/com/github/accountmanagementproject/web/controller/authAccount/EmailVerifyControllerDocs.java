package com.github.accountmanagementproject.web.controller.authAccount;

import com.github.accountmanagementproject.web.dto.responseSystem.CustomSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.RequestParam;
@Tag(name = "Email", description = "이메일 인증, 중복 확인 API")
public interface EmailVerifyControllerDocs {

    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 여부 확인")
    @ApiResponse(responseCode = "200", description = "확인 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "사용 가능",
                                    description = "⬆️⬆️사용가능한 이메일일때. 같은 이메일로 가입된 이메일이 없습니다.<br> responseData 값을 불리언타입으로 반환합니다(true or false)",
                                    value = """
                                            {
                                                "success": {
                                                  "code": 200,
                                                  "httpStatus": "OK",
                                                  "message": "이메일 사용 가능",
                                                  "responseData": true,
                                                  "timestamp": "2024-10-16T19:49:43.3174581"
                                                }
                                              }"""),
                            @ExampleObject(name = "사용 불가",
                                    description = "⬆️⬆️사용 불가한 이메일. 이미 가입된 이메일입니다.<br> responseData 값을 불리언타입으로 반환합니다(true or false)",
                                    value = """
                                            {
                                                "success": {
                                                  "code": 200,
                                                  "httpStatus": "OK",
                                                  "message": "이메일 중복",
                                                  "responseData": false,
                                                  "timestamp": "2024-10-16T19:59:18.4381165"
                                                }
                                              }""")

                    })
    )
    @ApiResponse(responseCode = "400", description = "쿼리파라미터 값이 이메일 형식이 아닐때",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "이메일 형식이 아닌 값",
                            description = "⬆️⬆️ 이메일이 아닌 값으로 요청시도시 반환되는 응답",
                            value = """
                                    {
                                         "error": {
                                           "code": 400,
                                           "httpStatus": "BAD_REQUEST",
                                           "systemMessage": "이메일 타입이 아닙니다.",
                                           "customMessage": "잘못된 요청",
                                           "request": "email=abcabc.com",
                                           "timestamp": "2024-10-16 20:00:11"
                                         }
                                       }""")
            )
    )
    CustomSuccessResponse duplicateCheckEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") @Parameter(description = "사용자 이메일") String email);

    @Operation(summary = "인증 메일 발송", description = "6자리 무작위 숫자가 적힌 인증 메일 발송")
    @ApiResponse(responseCode = "200", description = "발송 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "발송 성공",
                                    description = "⬆️⬆️발송 성공시 반환되는 응답",
                                    value = """
                                            {
                                                "success": {
                                                  "code": 200,
                                                  "httpStatus": "OK",
                                                  "message": "이메일 발송 성공",
                                                  "timestamp": "2024-10-16T19:49:43.3174581"
                                                }
                                              }""")
                    })
    )
    @ApiResponse(responseCode = "400", description = "쿼리파라미터 값이 이메일 형식이 아닐때",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "이메일 형식이 아닌 값",
                            description = "⬆️⬆️ 이메일이 아닌 값으로 요청시도시 반환되는 응답",
                            value = """
                                    {
                                         "error": {
                                           "code": 400,
                                           "httpStatus": "BAD_REQUEST",
                                           "systemMessage": "이메일 타입이 아닙니다.",
                                           "customMessage": "잘못된 요청",
                                           "request": "email=abcabc.com",
                                           "timestamp": "2024-10-16 20:00:11"
                                         }
                                       }""")
            )
    )
    CustomSuccessResponse sendVerifyCodeToEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.")@Parameter(description = "사용자 이메일") String email);

    @Operation(summary = "인증 번호 확인", description = "메일에 전송된 번호와 사용자가 입력한 번호 일치 여부 확인")
    @ApiResponse(responseCode = "200", description = "확인 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "인증 성공",
                                    description = "⬆️⬆️인증 성공시 반환되는 응답",
                                    value = """
                                            {
                                                "success": {
                                                  "code": 200,
                                                  "httpStatus": "OK",
                                                  "message": "이메일 인증 성공",
                                                  "responseData": true,
                                                  "timestamp": "2024-10-16T19:49:43.3174581"
                                                }
                                              }"""),
                            @ExampleObject(name = "인증 실패",
                                    description = "⬆️⬆️인증 실패시 반환되는 응답",
                                    value = """
                                            {
                                                "success": {
                                                  "code": 200,
                                                  "httpStatus": "OK",
                                                  "message": "이메일 인증 실패",
                                                  "responseData": false,
                                                  "timestamp": "2024-10-16T19:59:18.4381165"
                                                }
                                              }""")

                    })
    )
    @ApiResponse(responseCode = "400", description = "쿼리파라미터 값이 이메일 형식이 아닐때",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "이메일 형식이 아닌 값",
                            description = "⬆️⬆️ 이메일이 아닌 값으로 요청시도시 반환되는 응답",
                            value = """
                                    {
                                         "error": {
                                           "code": 400,
                                           "httpStatus": "BAD_REQUEST",
                                           "systemMessage": "이메일 타입이 아닙니다.",
                                           "customMessage": "잘못된 요청",
                                           "request": "email=abcabc.com",
                                           "timestamp": "2024-10-16 20:00:11"
                                         }
                                       }""")
            )
    )
    CustomSuccessResponse verifyEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.")@Parameter(description = "사용자 이메일") String email, @RequestParam @Parameter(description = "메일에 전송된 인증 번호") String code);

}
