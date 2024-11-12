package com.github.accountmanagementproject.web.controller.account.auth;

import com.github.accountmanagementproject.web.dto.account.auth.request.LoginRequest;
import com.github.accountmanagementproject.web.dto.account.auth.request.SignUpRequest;
import com.github.accountmanagementproject.web.dto.account.auth.response.TokenDto;
import com.github.accountmanagementproject.web.dto.account.oauth.request.KakaoLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.request.NaverLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthSignUpDto;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "회원 인증 관련 API")
public interface AuthControllerDocs {
    @Operation(summary = "회원 가입", description = "회원 가입에 필요한 정보들을 입력 받아 가입 진행")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공",
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
    @ApiResponse(responseCode = "409", description = "이메일, 핸드폰 번호 두 값중 중복 값 발생",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "핸드폰 번호 중복",
                                    description = "⬆️⬆️ 핸드폰 번호 중복 발생",
                                    value = """
                                            {
                                              "error": {
                                                "code": 409,
                                                "httpStatus": "CONFLICT",
                                                "systemMessage": "could not execute statement [(conn=628) Duplicate entry '01012345678' for key 'phone_number'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [phone_number]",
                                                "customMessage": "phoneNumber 중복",
                                                "request": {
                                                  "value": "01012345678",
                                                  "key": "phoneNumber"
                                                },
                                                "timestamp": "2024-10-20 19:36:23"
                                              }
                                            }"""),
                            @ExampleObject(name = "이메일 중복",
                                    description = "⬆️⬆️ 이메일 중복 발생",
                                    value = """
                                            {
                                                "error": {
                                                  "code": 409,
                                                  "httpStatus": "CONFLICT",
                                                  "systemMessage": "could not execute statement [(conn=628) Duplicate entry 'abc@abc.com' for key 'email'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [email]",
                                                  "customMessage": "email 중복",
                                                  "request": {
                                                    "value": "abc@abc.com",
                                                    "key": "email"
                                                  },
                                                  "timestamp": "2024-10-20 19:36:46"
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
    ResponseEntity<CustomSuccessResponse> signUp(@RequestBody SignUpRequest signUpRequest);

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


    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 이용해 새로운 액세스 토큰을 발급")
    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "토큰 재발급 성공",
                            description = "⬆️⬆️ 상태코드 200으로 내려주며 리스폰 데이터에 새로운 토큰 정보 담아 리턴",
                            value = """
                                    {
                                      "success": {
                                        "code": 200,
                                        "httpStatus": "OK",
                                        "message": "토큰 재발급 성공",
                                        "responseData": {
                                          "tokenType": "Bearer",
                                          "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjkwNjA0NjcsImV4cCI6MTcyOTA2NDA2Nywic3ViIjoiYWJjM0BhYmMuY29tIiwicm9sZXMiOiJST0xFX1VTRVIifQ.LeC81cXhFI1H_VlKcJlOzRmtR73ITIjqYdOsrPZqPZs",
                                          "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjkwNjA0NjcsImV4cCI6MTcyOTA2NDA2Nywic3ViIjoiYWJjM0BhYmMuY29tIiwicm9sZXMiOiJST0xFX1VTRVIifQ.LeC81cXhFI1H_VlKcJlOzRmtR73ITIjqYdOsrPZqPZs"
                                        },
                                        "timestamp": "2024-10-16T15:34:27.5487649"
                                      }
                                    }""")
            )
    )
    CustomSuccessResponse regenerateToken(@RequestBody TokenDto tokenDto);

    @Operation(summary = "카카오 로그인", description = "code값을 받아 카카오 로그인 진행<br>" +
            "https://kauth.kakao.com/oauth/authorize?client_id=d7453d5d4fe1c096ca03c1ed009a03ff&redirect_uri=https://localhost:8080/api/auth/kakao&response_type=code <br>" +
            "해당 url 로 접속후 쿼리파라미터 code 값을 받아와서 요청")
    @ApiResponse(responseCode = "200", description = "요청 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "로그인 성공",
                                    description = "⬆️⬆️로그인 성공 응답 ",
                                    value = """
                                            {
                                              "success": {
                                                "code": 200,
                                                "httpStatus": "OK",
                                                "message": "로그인 성공",
                                                "responseData": {
                                                  "tokenType": "Bearer",
                                                  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjk0MjAxMzYsImV4cCI6MTcyOTQyMzczNiwic3ViIjoic2lodTkzQGdtYWlsLmNvbSIsInJvbGVzIjoiUk9MRV9VU0VSIn0.kIO8gCXsZARdocIGJx5Fc27JuEnBX7W6Q1b2t0mdd48",
                                                  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjk0MjAxMzYsImV4cCI6MTcyOTQyMDczNn0.2GYc1Phu3UodVsudDjdA1IEToRtn9nH0RF4JTyVWfnw"
                                                },
                                                "timestamp": "2024-10-20T19:28:56.4356391"
                                              }
                                            }"""),
                            @ExampleObject(name = "임시 계정 생성",
                                    description = "⬆️⬆️회원가입이 필요하여 임시계정 생성후 소셜 계정의 정보를 반환",
                                    value = """
                                            {
                                              "success": {
                                                "code": 201,
                                                "httpStatus": "CREATED",
                                                "message": "임시 계정 생성",
                                                "responseData": {
                                                  "socialId": "3262302620",
                                                  "provider": "카카오",
                                                  "nickname": "닉네임",
                                                  "email": "abc@abc.com",
                                                  "profileImg": "http://https://cdn-icons-png.flaticon.com/512/659/659999.png"
                                                },
                                                "timestamp": "2024-10-19T23:41:00.7807525"
                                              }
                                            }""")

                    })
    )
    @ApiResponse(responseCode = "400", description = "전달된 코드 만료",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "코드 만료",
                            description = "⬆️⬆️ 상태코드 400 리스폰 데이터에 만료된 코드 정보 담아 리턴",
                            value = """
                                    {
                                       "error": {
                                         "code": 400,
                                         "httpStatus": "BAD_REQUEST",
                                         "systemMessage": "400 Bad Request: \\"{\\"error\\":\\"invalid_grant\\",\\"error_description\\":\\"authorization code not found for code=OubMrl8Ta_y1o9ri9t-hyvS2bV8xlN7UdiP8R-Xtdu_9tStUdZ3DAgAAAAQKKwyoAAABkql33zBb9Pmr5eg_ZA\\",\\"error_code\\":\\"KOE320\\"}\\"",
                                         "customMessage": "전달된 code가 만료되었습니다.",
                                         "request": "OubMrl8Ta_y1o9ri9t-hyvS2bV8xlN7UdiP8R-Xtdu_9tStUdZ3DAgAAAAQKKwyoAAABkql33zBb9Pmr5eg_ZA",
                                         "timestamp": "2024-10-20 19:29:17"
                                       }
                                     }"""))
    )
    ResponseEntity<CustomSuccessResponse> loginKakao(@RequestBody KakaoLoginParams params);


    @Operation(summary = "네이버 로그인", description = "code값, state값을 받아 네이버 로그인 진행<br>" +
            "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JXg1CteqW8SXf8k7J3kN&state=hLiDdL2uhPtsftcU&redirect_uri=http://localhost:8080/api/auth/naver <br>" +
            "해당 url 로 접속후 쿼리파라미터 code 값과 state 값을 받아와서 요청")
    @ApiResponse(responseCode = "200", description = "요청 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "로그인 성공",
                                    description = "⬆️⬆️로그인 성공 응답 ",
                                    value = """
                                            {
                                              "success": {
                                                "code": 200,
                                                "httpStatus": "OK",
                                                "message": "로그인 성공",
                                                "responseData": {
                                                  "tokenType": "Bearer",
                                                  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjk0MjAxMzYsImV4cCI6MTcyOTQyMzczNiwic3ViIjoic2lodTkzQGdtYWlsLmNvbSIsInJvbGVzIjoiUk9MRV9VU0VSIn0.kIO8gCXsZARdocIGJx5Fc27JuEnBX7W6Q1b2t0mdd48",
                                                  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjk0MjAxMzYsImV4cCI6MTcyOTQyMDczNn0.2GYc1Phu3UodVsudDjdA1IEToRtn9nH0RF4JTyVWfnw"
                                                },
                                                "timestamp": "2024-10-20T19:28:56.4356391"
                                              }
                                            }"""),
                            @ExampleObject(name = "임시 계정 생성",
                                    description = "⬆️⬆️회원가입이 필요하여 임시계정 생성후 소셜 계정의 정보를 반환",
                                    value = """
                                            {
                                              "success": {
                                                "code": 201,
                                                "httpStatus": "CREATED",
                                                "message": "임시 계정 생성",
                                                "responseData": {
                                                  "socialId": "PcKzS_ix6QsH-F12zuKQ4P-_to12fazxMTTN2vwBKQ",
                                                  "provider": "네이버",
                                                  "nickname": "닉네임",
                                                  "email": "abc@naver.com",
                                                  "profileImg": "https://ssl.pstatic.net/static/pwe/address/img_profile.png"
                                                },
                                                "timestamp": "2024-10-20T19:31:51.6474585"
                                              }
                                            }""")

                    })
    )
    @ApiResponse(responseCode = "400", description = "전달된 코드 만료",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "코드 만료",
                            description = "⬆️⬆️ 상태코드 400 리스폰 데이터에 만료된 코드 정보 담아 리턴",
                            value = """
                                    {
                                       "error": {
                                         "code": 400,
                                         "httpStatus": "BAD_REQUEST",
                                         "systemMessage": "400 Bad Request: \\"{\\"error\\":\\"invalid_grant\\",\\"error_description\\":\\"authorization code not found for code=OubMrl8Ta_y1o9ri9t-hyvS2bV8xlN7UdiP8R-Xtdu_9tStUdZ3DAgAAAAQKKwyoAAABkql33zBb9Pmr5eg_ZA\\",\\"error_code\\":\\"KOE320\\"}\\"",
                                         "customMessage": "전달된 code가 만료되었습니다.",
                                         "request": "OubMrl8Ta_y1o9ri9t-hyvS2bV8xlN7UdiP8R-Xtdu_9tStUdZ3DAgAAAAQKKwyoAAABkql33zBb9Pmr5eg_ZA",
                                         "timestamp": "2024-10-20 19:29:17"
                                       }
                                     }"""))
    )
    ResponseEntity<CustomSuccessResponse> loginNaver(@RequestBody NaverLoginParams params);

    @Operation(summary = "소셜 로그인 회원가입", description = "소셜 로그인 회원가입에 필요한 정보들을 입력 받아 가입 진행")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "회원 가입 성공 예",
                            description = "⬆️⬆️ 상태코드 201 리스폰 데이터는 따로 없습니다.",
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
    @ApiResponse(responseCode = "409", description = "이메일, 핸드폰 번호 두 값중 중복 값 발생",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "핸드폰 번호 중복",
                                    description = "⬆️⬆️ 핸드폰 번호 중복 발생",
                                    value = """
                                            {
                                              "error": {
                                                "code": 409,
                                                "httpStatus": "CONFLICT",
                                                "systemMessage": "could not execute statement [(conn=628) Duplicate entry '01012345678' for key 'phone_number'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [phone_number]",
                                                "customMessage": "phoneNumber 중복",
                                                "request": {
                                                  "value": "01012345678",
                                                  "key": "phoneNumber"
                                                },
                                                "timestamp": "2024-10-20 19:36:23"
                                              }
                                            }"""),
                            @ExampleObject(name = "이메일 중복",
                                    description = "⬆️⬆️ 이메일 중복 발생",
                                    value = """
                                            {
                                                "error": {
                                                  "code": 409,
                                                  "httpStatus": "CONFLICT",
                                                  "systemMessage": "could not execute statement [(conn=628) Duplicate entry 'abc@abc.com' for key 'email'] [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; SQL [insert into users (date_of_birth,email,gender,nickname,password,phone_number,profile_img) values (?,?,?,?,?,?,?)]; constraint [email]",
                                                  "customMessage": "email 중복",
                                                  "request": {
                                                    "value": "abc@abc.com",
                                                    "key": "email"
                                                  },
                                                  "timestamp": "2024-10-20 19:36:46"
                                                }
                                              }""")
                    })
    )
    @ApiResponse(responseCode = "400", description = "필수 값 누락 또는 기타 필수 조건 미 충족",
            content = @Content(mediaType = "application/json",
                    examples =
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
                                            }""")
                    )
    )
    ResponseEntity<CustomSuccessResponse> oAuthSignUp(@RequestBody @Valid OAuthSignUpDto oAuthSignUpDto);

}
