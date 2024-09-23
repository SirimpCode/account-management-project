package com.github.accountmanagementproject.web.controller.authAccount;


import com.github.accountmanagementproject.service.authAccount.SignUpLoginService;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.web.dto.accountAuth.AccountDto;
import com.github.accountmanagementproject.web.dto.accountAuth.LoginRequest;
import com.github.accountmanagementproject.web.dto.accountAuth.TokenDto;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignUpLoginService signUpLoginService;




    @PostMapping("/sign-up")
    public ResponseEntity<CustomSuccessResponse> signUp(@RequestBody AccountDto accountDto){
        signUpLoginService.signUp(accountDto);
        CustomSuccessResponse signUpResponse = new CustomSuccessResponse.SuccessDetail()
                .message("회원가입 완료")
                .httpStatus(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(signUpResponse, signUpResponse.getSuccess().getHttpStatus());
    }
    @PostMapping("/login")
    public CustomSuccessResponse login(@RequestBody LoginRequest loginRequest){
        return new CustomSuccessResponse.SuccessDetail()
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK)
                .responseData(signUpLoginService.loginResponseToken(loginRequest))
                .build();
    }
    @PostMapping("/refresh")
    public CustomSuccessResponse regenerateToken(@RequestBody TokenDto tokenDto){
        return new CustomSuccessResponse.SuccessDetail()
                .message("토큰 재발급")
                .httpStatus(HttpStatus.OK)
                .responseData(signUpLoginService.refreshTokenByTokenDto(tokenDto))
                .build();
    }


    @GetMapping("/testerr")
    public CustomErrorResponse errorTest(){
        signUpLoginService.errorTest();
        throw new CustomBadRequestException.ExceptionBuilder()
                .customMessage("메세지")
                .systemMessage("시스템 메세지")
                .request("요청값")
                .build();
    }
    @GetMapping("/testsuc")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomSuccessResponse successTest(@AuthenticationPrincipal String principal) throws IOException {

        //3번째 요소 데이터는 없어도됨
        return new CustomSuccessResponse.SuccessDetail()
                .message("테슽흐")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
//    @GetMapping("/security-test")
//    public String securityTest(@RequestParam(name = "user-id") Integer userId){
//        return signUpLoginService.securityTest(userId);
//    }

    @GetMapping("/auth-test")
    public String authTest(@AuthenticationPrincipal String customUserDetails){

        return customUserDetails;
    }

    @GetMapping("authorize-test")
    public String authorizeTest(@AuthenticationPrincipal String email){
        return email;
    }

}
