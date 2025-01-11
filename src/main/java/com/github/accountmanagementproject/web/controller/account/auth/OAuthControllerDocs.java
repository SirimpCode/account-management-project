package com.github.accountmanagementproject.web.controller.account.auth;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.account.oauth.request.KakaoLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.request.NaverLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthDtoInterface;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthSignUpDto;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "OAuthController", description = "소셜 인증에 필요한 정보를 요청하기 위한 API")
public interface OAuthControllerDocs {

    @Operation(summary = "OAuth 인증 Url 리다이렉션 (백 테스트용)", description = "소셜 공급자에 코드 발급 요청을 위한 URL로 이동 시키기")
    ResponseEntity<Void> requestOAuthCodeUrlRedirect(@Parameter(schema = @Schema(type = "string", example = "kakao")) OAuthProvider provider, HttpServletRequest httpServletRequest);


    @Operation(summary = "OAuth 인증 요청 (백에서 처리)", description = "인증에 필요한 code, state 등을 받아서 인증 진행")
    ResponseEntity<CustomSuccessResponse<OAuthDtoInterface>> oAuthRequest(@Parameter(schema = @Schema(type = "string", example = "kakao")) OAuthProvider provider,
                                                                          HttpServletRequest httpServletRequest);
//    @Operation(summary = "카카오 OAuth 인증 요청", description = "인증에 필요한 code, state 등을 받아서 인증 진행")
//    CustomSuccessResponse kakaoOAuthRequest(KakaoLoginParams params);
//    @Operation(summary = "구글 OAuth 인증 요청", description = "인증에 필요한 code, state 등을 받아서 인증 진행")
//    CustomSuccessResponse googleOAuthRequest(GoogleLoginParams params);

    @Operation(summary = "OAuth 인증 페이지 요청", description = "인증 페이지 요청")
    CustomSuccessResponse<String> getProviderAuthUrl(@Parameter(schema = @Schema(type = "string", example = "kakao")) OAuthProvider provider, @RequestParam String redirectUri);

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
    ResponseEntity<CustomSuccessResponse<OAuthDtoInterface>> loginKakao(@RequestBody KakaoLoginParams params);


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
    ResponseEntity<CustomSuccessResponse<OAuthDtoInterface>> loginNaver(@RequestBody NaverLoginParams params);

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
    ResponseEntity<CustomSuccessResponse<Void>> oAuthSignUp(@RequestBody @Valid OAuthSignUpDto oAuthSignUpDto);


}