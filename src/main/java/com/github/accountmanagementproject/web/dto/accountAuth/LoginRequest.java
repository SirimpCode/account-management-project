package com.github.accountmanagementproject.web.dto.accountAuth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "이메일 또는 핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^(01\\d{9}|[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+)$", message = "이메일 또는 핸드폰 번호 형식이 아닙니다.")
    @Schema(description = "이메일 또는 전화번호", example = "admin@amp.com")
    private String emailOrPhoneNumber;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
            message = "비밀번호 오류")
    @Schema(description = "비밀번호", example = "12341234a!")
    private String password;

    public Authentication toAuthentication(){
        return new UsernamePasswordAuthenticationToken(emailOrPhoneNumber, password);
    }
}
