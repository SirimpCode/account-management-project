package com.github.accountmanagementproject.web.dto.account.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;
import com.github.accountmanagementproject.web.dto.account.AccountParent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class MyInfoResponse extends AccountParent {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "마지막 로그인 날짜")
    private String lastLogin;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "계정 상태")
    private UserStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "유저의 허용 권한")
    private Set<RolesEnum> roles;
}
