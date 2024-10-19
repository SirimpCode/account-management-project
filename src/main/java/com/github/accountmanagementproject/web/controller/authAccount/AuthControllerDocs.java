package com.github.accountmanagementproject.web.controller.authAccount;

import com.github.accountmanagementproject.web.dto.accountAuth.AccountInfoDto;
import com.github.accountmanagementproject.web.dto.accountAuth.LoginRequest;
import com.github.accountmanagementproject.web.dto.accountAuth.TokenDto;
import com.github.accountmanagementproject.web.dto.responseSystem.CustomSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "회원 인증 관련 API")
public interface AuthControllerDocs {
    @Operation(summary = "회원 가입", description = "회원 가입에 필요한 정보들을 입력 받아 가입 진행")
    @ApiResponse(responseCode = "201",description = "회원 가입 성공",
            content = @Content(mediaType = "application/json",
                    examples =
                            @ExampleObject(name = "회원 가입 성공 예",
                                    description = "⬆️⬆️ 상태코드 201로 내려주며 리스폰 데이터는 따로 없습니다.",
                                    value = """
                                            {
                                              "success": {
                                                "code": 201,
                                                "httpStatus": "CREATED",
                                                "message": "회원가입 완료",
                                                "timestamp": "2024-10-16T15:31:55.6752972"
                                              }
                                            }""")
                    )
    )
    @ApiResponse(responseCode = "409", description = "이메일, 핸드폰 번호, 닉네임 세 값중 중복 값 발생",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "닉네임 중복",
                                    description = "⬆️⬆️ 닉네임 중복 발생",
                                    value = """
                                            {
                                              "error": {
                                                "code": 409,
                                                "httpStatus": "CONFLICT",
                                                "systemMessage": "could not execute statement [(conn=462) Duplicate entry '이카르디' for key 'nickname'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [nickname]",
                                                "customMessage": "이메일, 핸드폰 번호, 닉네임 세 값중 중복 값 발생",
                                                "request": {
                                                  "phoneNumber": "01032345678",
                                                  "email": "abc3@abc.com",
                                                  "nickname": "이카르디"
                                                },
                                                "timestamp": "2024-10-16 15:27:39"
                                              }
                                            }"""),
                            @ExampleObject(name = "핸드폰 번호 중복",
                                    description = "⬆️⬆️ 핸드폰 번호 중복 발생",
                                    value = """
                                            {
                                                "error": {
                                                  "code": 409,
                                                  "httpStatus": "CONFLICT",
                                                  "systemMessage": "could not execute statement [(conn=492) Duplicate entry '01012345678' for key 'phone_number'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [phone_number]",
                                                  "customMessage": "이메일, 핸드폰 번호, 닉네임 세 값중 중복 값 발생",
                                                  "request": {
                                                    "phoneNumber": "01012345678",
                                                    "email": "abc@abc.com",
                                                    "nickname": "이브라히ㅁ모비치"
                                                  },
                                                  "timestamp": "2024-10-16 15:44:34"
                                                }
                                              }"""),
                            @ExampleObject(name = "이메일 중복",
                                    description = "⬆️⬆️ 이메일 중복 발생",
                                    value = """
                                            {
                                               "error": {
                                                 "code": 409,
                                                 "httpStatus": "CONFLICT",
                                                 "systemMessage": "could not execute statement [(conn=492) Duplicate entry 'abc@abc.com' for key 'email'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [email]",
                                                 "customMessage": "이메일, 핸드폰 번호, 닉네임 세 값중 중복 값 발생",
                                                 "request": {
                                                   "phoneNumber": "01072345678",
                                                   "email": "abc@abc.com",
                                                   "nickname": "이브라히ㅁ모비치"
                                                 },
                                                 "timestamp": "2024-10-16 15:45:30"
                                               }
                                             }""")
                    })
    )
    @ApiResponse(responseCode = "400", description = "필수 값 누락 또는 기타 필수 조건 미 충족",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "회원가입에 필요한 필수 값 누락",
                                    description = "⬆️⬆️ 이메일 누락",
                                    value = """
                                            {
                                              "error": {
                                                "code": 400,
                                                "httpStatus": "BAD_REQUEST",
                                                "systemMessage": "유효성 검사 실패",
                                                "customMessage": "이메일은 필수 입니다.",
                                                "request": "email : null",
                                                "timestamp": "2024-10-16 15:29:25"
                                              }
                                            }"""),
                            @ExampleObject(name = "조건 미충족",
                                    description = "⬆️⬆️ 비밀번호와 비밀번호 확인이 다름",
                                    value = """
                                            {
                                               "error": {
                                                 "code": 400,
                                                 "httpStatus": "BAD_REQUEST",
                                                 "systemMessage": "유효성 검사 실패",
                                                 "customMessage": "비밀번호와 비밀번호 확인이 같아야 합니다.",
                                                 "request": "passwordEquals : false",
                                                 "timestamp": "2024-10-16 15:40:35"
                                               }
                                             }""")
                    })
    )
    ResponseEntity<CustomSuccessResponse> signUp(@RequestBody AccountInfoDto accountInfoDto);

    @Operation(summary = "로그인", description = "로그인에 필요한 정보들을 입력 받아 로그인 진행")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(mediaType = "application/json",
                    examples =
                            @ExampleObject(name = "로그인 성공",
                                    description = "⬆️⬆️ 상태코드 200으로 내려주며 리스폰 데이터에 토큰 정보 담아 리턴",
                                    value = """
                                            {
                                              "success": {
                                                "code": 200,
                                                "httpStatus": "OK",
                                                "message": "로그인 성공",
                                                "responseData": {
                                                  "tokenType": "Bearer",
                                                  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjkwNjA0NjcsImV4cCI6MTcyOTA2NDA2Nywic3ViIjoiYWJjM0BhYmMuY29tIiwicm9sZXMiOiJST0xFX1VTRVIifQ.LeC81cXhFI1H_VlKcJlOzRmtR73ITIjqYdOsrPZqPZs",
                                                  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjkwNjA0NjcsImV4cCI6MTcyOTA2MTA2N30.y4lrehsGYXDBYM1i92LlGTkg2MbYmkoRt5baWHjh5bg"
                                                },
                                                "timestamp": "2024-10-16T15:34:27.5487649"
                                              }
                                            }""")
                    )
    )
    @ApiResponse(responseCode = "401", description = "비밀번호 오류",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "비밀번호 오류",
                                    description = "⬆️⬆️ 상태코드 401로 내려주며 request에 실패 횟수와 날짜 정보, 해당 계정 닉네임 담아 리턴 실패횟수가 5가 되면 계정이 5분간 잠깁니다.",
                                    value = """
                                            {
                                              "error": {
                                                "code": 401,
                                                "httpStatus": "UNAUTHORIZED",
                                                "systemMessage": "자격 증명에 실패하였습니다.",
                                                "customMessage": "비밀번호 오류",
                                                "request": {
                                                  "nickname": "이카르디",
                                                  "failureCount": 1,
                                                  "failureDate": "2024-10-16T15:35:31.995224"
                                                },
                                                "timestamp": "2024-10-16 15:35:31"
                                              }
                                            }"""),
                            @ExampleObject(name = "비밀번호 5번째 오류",
                                    description = "⬆️⬆️ request에 닉네임, 계정 상태, 마지막 로그인 실패 날짜를 담아 리턴 해당 시간으로부터 5분간 계정이 잠깁니다.",
                                    value = """
                                            {
                                               "error": {
                                                 "code": 401,
                                                 "httpStatus": "UNAUTHORIZED",
                                                 "systemMessage": "자격 증명에 실패하였습니다. 계정이 잠깁니다.",
                                                 "customMessage": "비밀번호 오류",
                                                 "request": {
                                                   "nickname": "운영자",
                                                   "status": "잠긴 계정",
                                                   "failureDate": "2024-10-16T15:46:59.0475996"
                                                 },
                                                 "timestamp": "2024-10-16 15:46:59"
                                               }
                                             }""")

                    })
    )
    @ApiResponse(responseCode = "423", description = "잠긴 계정에 로그인 시도",
            content = @Content(mediaType = "application/json",
                    examples =
                            @ExampleObject(name = "잠긴 계정",
                                    description = "⬆️⬆️ 상태코드 423로 내려주며 request에 해당 계정의 닉네임과 계정 상태 마지막로그인 실패 날짜 담아 리턴 이 시간으로부터 5분이 지나면 잠금이 풀립니다.",
                                    value = """
                                            {
                                              "error": {
                                                "code": 423,
                                                "httpStatus": "LOCKED",
                                                "customMessage": "로그인 실패",
                                                "request": {
                                                  "nickname": "운영자",
                                                  "status": "잠긴 계정",
                                                  "failureDate": "2024-10-16T15:46:59"
                                                },
                                                "timestamp": "2024-10-16 15:48:06"
                                              }
                                            }"""))
    )
    CustomSuccessResponse login(@RequestBody LoginRequest loginRequest);

    CustomSuccessResponse regenerateToken(@RequestBody TokenDto tokenDto);
}
