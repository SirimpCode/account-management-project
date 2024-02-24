package com.github.accountmanagementproject.web.controller.authAccount;

import com.github.accountmanagementproject.service.authAccount.AccountService;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/my-info")
    public CustomSuccessResponse getMyInfo(@AuthenticationPrincipal String principal){
        return accountService.myInfoByEmail(principal);
    }

}
