package com.github.accountmanagementproject.web.controller.account.auth;


import com.github.accountmanagementproject.common.myenum.Gender;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import com.github.accountmanagementproject.common.myenum.RolesEnum;
import com.github.accountmanagementproject.service.account.auth.SignUpLoginService;
import com.github.accountmanagementproject.service.account.oauth.OAuthLoginService;
import com.github.accountmanagementproject.web.dto.account.auth.request.LoginRequest;
import com.github.accountmanagementproject.web.dto.account.auth.request.SignUpRequest;
import com.github.accountmanagementproject.web.dto.account.auth.response.TokenDto;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {
    private final SignUpLoginService signUpLoginService;
    private final OAuthLoginService oAuthLoginService;



    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<CustomSuccessResponse<Void>> signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        signUpLoginService.signUp(signUpRequest);
        CustomSuccessResponse<Void> signUpResponse = CustomSuccessResponse
                .emptyData(HttpStatus.CREATED, "회원가입 완료");
        return ResponseEntity
                .status(signUpResponse.getHttpStatus())
                .body(signUpResponse);
    }

    @Override
    @PostMapping("/login")
    public CustomSuccessResponse<TokenDto> login(@RequestBody @Valid LoginRequest loginRequest){
        return CustomSuccessResponse
                .ofOk("로그인 성공", signUpLoginService.loginResponseToken(loginRequest));
    }


    @Override
    @PostMapping("/refresh")
    public CustomSuccessResponse<TokenDto> regenerateToken(@RequestBody @Valid TokenDto tokenDto){
        return CustomSuccessResponse
                .ofOk("토큰 재발급", signUpLoginService.refreshTokenByTokenDto(tokenDto));
    }



    @GetMapping("/tt")
    public CustomSuccessResponse<OAuthProvider> tt(@Parameter(schema = @Schema(type = "string", example = "카카오")) @RequestParam OAuthProvider provider){
        return CustomSuccessResponse.ofOk(provider.name(), provider);
    }
    @GetMapping("/ttt")
    public CustomSuccessResponse<Gender> ttt(@Parameter(schema = @Schema(type = "string", example = "카카오")) @RequestParam Gender gender){
        return CustomSuccessResponse.ofOk(gender.name(), gender);
    }
    @GetMapping("/tttt")
    public CustomSuccessResponse<RolesEnum> tttt(@Parameter(schema = @Schema(type = "string", example = "카카오")) @RequestParam RolesEnum role){
        return CustomSuccessResponse.ofOk(role.name(), role);
    }



}
