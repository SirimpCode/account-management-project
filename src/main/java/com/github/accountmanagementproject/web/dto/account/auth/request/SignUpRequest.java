package com.github.accountmanagementproject.web.dto.account.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.accountmanagementproject.web.dto.account.AccountDefaultSettingExtendsParent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequest extends AccountDefaultSettingExtendsParent {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호는 필수 입니다.")
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
            message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상 20자 이하여야 합니다.")
    @Schema(description = "비밀번호 (*영문과 특수문자, 숫자를 포함)", example = "12341234a!", minLength = 8, maxLength = 20, pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호 확인은 필수 입니다.")
    @Schema(description = "비밀번호 확인", example = "12341234a!", minLength = 8, maxLength = 20, pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
    private String passwordConfirm;

    @AssertTrue(message = "비밀번호와 비밀번호 확인이 같아야 합니다.")
    private boolean isPasswordEquals(){
        return this.password.equals(this.passwordConfirm);
    }

    public void passwordReplace(String password){
        this.password = password;
    }
}
