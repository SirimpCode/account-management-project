package com.github.accountmanagementproject.web.controller.account;

import com.github.accountmanagementproject.service.account.AccountService;
import com.github.accountmanagementproject.web.dto.account.auth.response.MyInfoResponse;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import lombok.RequiredArgsConstructor;
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
    public CustomSuccessResponse<MyInfoResponse> getMyInfo(@AuthenticationPrincipal String principal){
        return CustomSuccessResponse
                .ofOk("유저 정보 조회 성공", accountService.myInfoByEmail(principal));
    }

}
