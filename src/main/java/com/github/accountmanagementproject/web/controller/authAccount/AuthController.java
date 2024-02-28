package com.github.accountmanagementproject.web.controller.authAccount;


import com.github.accountmanagementproject.service.authAccount.SignUpLoginService;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import com.github.accountmanagementproject.web.dto.account.JwtToken;
import com.github.accountmanagementproject.web.dto.account.LoginRequest;
import com.github.accountmanagementproject.web.dto.response.CustomErrorResponse;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    @PostMapping("/sign-in")
    public CustomSuccessResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        JwtToken token = signUpLoginService.loginResponseToken(loginRequest);
        httpServletResponse.setHeader("Authorization", token.getGrantType()+" "+token.getAccessToken());
        return new CustomSuccessResponse.SuccessDetail()
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK)
                .build();
    }


    @GetMapping("/testerr")
    public CustomErrorResponse errorTest(){
        throw new CustomBadRequestException("메롱입니다.", "요청값입니다.");
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
    @GetMapping("/security-test")
    public String securityTest(@RequestParam(name = "user-id") Integer userId){
        return signUpLoginService.securityTest(userId);
    }
    @GetMapping("/token-test")
    public String tokenTest(HttpServletRequest httpServletRequest){
        return signUpLoginService.tokenTest(httpServletRequest.getHeader("Authorization"));
    }

    @GetMapping("/auth-test")
    public String authTest(@AuthenticationPrincipal String customUserDetails){

        return customUserDetails;
    }

    @GetMapping("authorize-test")
    public String authorizeTest(@AuthenticationPrincipal String email){
        return email;
    }


}
