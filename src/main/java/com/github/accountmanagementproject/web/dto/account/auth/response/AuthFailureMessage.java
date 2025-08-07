package com.github.accountmanagementproject.web.dto.account.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.accountmanagementproject.common.myenum.UserStatus;
import com.github.accountmanagementproject.common.security.userdetails.CustomUserDetails;
import com.github.accountmanagementproject.repository.account.user.MyUser;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthFailureMessage {
    private final String nickname;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer failureCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime failureDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime withdrawalDate;

    public AuthFailureMessage(CustomUserDetails userDetails){
        this.nickname = userDetails.getNickname();
        this.failureCount = userDetails.getFailureCount() == 0 || userDetails.isDisabled() ? null:userDetails.getFailureCount();
        this.status = userDetails.getStatus()== UserStatus.NORMAL?null:userDetails.getStatus().getValue();
        this.failureDate = userDetails.isDisabled()?null:userDetails.getFailureDate();
        this.withdrawalDate = userDetails.getWithdrawalDate();
    }
}
