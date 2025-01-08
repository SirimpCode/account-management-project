package com.github.accountmanagementproject.web.controller.account;

import com.github.accountmanagementproject.service.account.EmailVerifyService;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailVerifyController implements EmailVerifyControllerDocs{
    private final EmailVerifyService emailVerifyService;
    @GetMapping("/duplicate")
    public CustomSuccessResponse<Boolean> duplicateCheckEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email){
        boolean isEmailAvailable = emailVerifyService.duplicateCheckEmail(email);
        return CustomSuccessResponse
                .ofOk(isEmailAvailable ? "이메일 사용 가능":"이메일 중복",
                isEmailAvailable);
    }
    @PostMapping("/send")
    public CustomSuccessResponse<Void> sendVerifyCodeToEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email){
        emailVerifyService.sendVerifyCodeToEmail(email);
        return CustomSuccessResponse.emptyDataOk("이메일 발송 성공");
    }
    @GetMapping("/verify")
    public CustomSuccessResponse<Boolean> verifyEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email, @RequestParam String code){
        boolean isVerified = emailVerifyService.verifyEmail(email, code);
        return CustomSuccessResponse
                .ofOk(isVerified ? "이메일 인증 성공":"이메일 인증 실패", isVerified);
    }

}
