package com.github.accountmanagementproject.web.controller.authAccount;

import com.github.accountmanagementproject.service.authAccount.AccountService;
import com.github.accountmanagementproject.web.dto.responseSystem.CustomSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController implements AccountControllerDocs{
    private final AccountService accountService;
    @GetMapping("/my-info")
    public CustomSuccessResponse getMyInfo(@AuthenticationPrincipal String principal){

        return new CustomSuccessResponse.SuccessDetail()
                .httpStatus(HttpStatus.OK)
                .message("유저 정보 조회 성공")
                .responseData(accountService.myInfoByEmail(principal))
                .build();
    }

}
