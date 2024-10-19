package com.github.accountmanagementproject.web.controller.authAccount;

import com.github.accountmanagementproject.web.dto.responseSystem.CustomSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Account", description = "계정 관련 API")
public interface AccountControllerDocs {
    @Operation(summary = "계정 정보 보기", description = "로그인된 계정의 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "계정 정보 조회 성공",
                            description = "⬆️⬆️ 상태코드 200으로 내려주며 리스폰 데이터에 계정 정보 담아 리턴",
                            value = """
                                    {
                                      "success": {
                                        "code": 200,
                                        "httpStatus": "OK",
                                        "message": "유저 정보 조회 성공",
                                        "responseData": {
                                          "profileImg": "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/elderly-man-icon.png",
                                          "email": "abc@abc.com",
                                          "nickname": "이브라히모비치",
                                          "phoneNumber": "01012345678",
                                          "gender": "남성",
                                          "dateOfBirth": "1999년 1월 8일",
                                          "lastLogin": "2024-10-16T15:57:11",
                                          "status": "정상 계정",
                                          "roles": [
                                            "유저"
                                          ]
                                        },
                                        "timestamp": "2024-10-16T15:57:33.679259"
                                      }
                                    }""")
            )
    )
    @ApiResponse(responseCode = "401", description = "인증 실패",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "인증 실패로 인한(토큰만료, 토큰없음, 토큰변형 등) 조회 실패",
                            description = "⬆️⬆️ 상태코드 401 systemMessage와 customMessage에 실패 이유를 담아 리턴",
                            value = """
                                    {
                                       "error": {
                                         "code": 401,
                                         "httpStatus": "UNAUTHORIZED",
                                         "systemMessage": "Full authentication is required to access this resource",
                                         "customMessage": "토큰 정보 없음",
                                         "timestamp": "2024-10-16 15:59:41"
                                       }
                                     }""")
            )
    )
    CustomSuccessResponse getMyInfo(@AuthenticationPrincipal String principal);

}
