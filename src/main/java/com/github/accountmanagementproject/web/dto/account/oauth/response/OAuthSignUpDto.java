package com.github.accountmanagementproject.web.dto.account.oauth.response;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.web.dto.account.AccountParent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthSignUpDto extends AccountParent {
    @NotBlank(message = "소셜 식별자 값은 필수입니다.")
    @Schema(description = "소셜 아이디", example = "Z9Vp6uyQ1S03CtxKHCnFS80KItHrRxIuwWse12EIupw")
    private String socialId;
    @NotNull(message = "소셜 공급자 값은 필수입니다.")
    private OAuthProvider provider;
}
