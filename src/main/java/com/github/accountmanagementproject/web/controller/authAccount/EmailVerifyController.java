package com.github.accountmanagementproject.web.controller.authAccount;

import com.github.accountmanagementproject.service.authAccount.EmailVerifyService;
import com.github.accountmanagementproject.web.dto.responseSystem.CustomSuccessResponse;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailVerifyController implements EmailVerifyControllerDocs{
    private final EmailVerifyService emailVerifyService;
    @GetMapping("/duplicate")
    public CustomSuccessResponse duplicateCheckEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email){
        boolean isEmailAvailable = emailVerifyService.duplicateCheckEmail(email);
        return new CustomSuccessResponse.SuccessDetail()
                .httpStatus(HttpStatus.OK)
                .message(isEmailAvailable ? "이메일 사용 가능":"이메일 중복")
                .responseData(isEmailAvailable)
                .build();
    }
    @PostMapping("/send")
    public CustomSuccessResponse sendVerifyCodeToEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email){
        emailVerifyService.sendVerifyCodeToEmail(email);
        return new CustomSuccessResponse.SuccessDetail()
                .httpStatus(HttpStatus.OK)
                .message("이메일 발송 성공")
                .build();
    }
    @GetMapping("/verify")
    public CustomSuccessResponse verifyEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email, @RequestParam String code){
        boolean isVerified = emailVerifyService.verifyEmail(email, code);
        return new CustomSuccessResponse.SuccessDetail()
                .httpStatus(HttpStatus.OK)
                .message(isVerified ? "이메일 인증 성공":"이메일 인증 실패")
                .responseData(isVerified)
                .build();
    }

}
