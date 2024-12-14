package com.github.accountmanagementproject.web.controller.account.auth;


import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.service.account.auth.SignUpLoginService;
import com.github.accountmanagementproject.service.account.oauth.OAuthLoginService;
import com.github.accountmanagementproject.web.dto.account.auth.request.LoginRequest;
import com.github.accountmanagementproject.web.dto.account.auth.request.SignUpRequest;
import com.github.accountmanagementproject.web.dto.account.auth.response.TokenDto;
import com.github.accountmanagementproject.web.dto.account.oauth.request.GoogleLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.request.KakaoLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.request.NaverLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.request.OAuthLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.response.AuthResult;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthSignUpDto;
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
    public ResponseEntity<CustomSuccessResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        signUpLoginService.signUp(signUpRequest);
        CustomSuccessResponse signUpResponse = createSignUpResponse();
        return new ResponseEntity<>(signUpResponse, signUpResponse.getSuccess().getHttpStatus());
    }

    @Override
    @PostMapping("/login")
    public CustomSuccessResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return new CustomSuccessResponse.SuccessDetail()
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK)
                .responseData(signUpLoginService.loginResponseToken(loginRequest))
                .build();
    }


    @Override
    @PostMapping("/refresh")
    public CustomSuccessResponse regenerateToken(@RequestBody @Valid TokenDto tokenDto){
        return new CustomSuccessResponse.SuccessDetail()
                .message("토큰 재발급")
                .httpStatus(HttpStatus.OK)
                .responseData(signUpLoginService.refreshTokenByTokenDto(tokenDto))
                .build();
    }

    @PostMapping("/kakao")
    public ResponseEntity<CustomSuccessResponse> loginKakao(@RequestBody KakaoLoginParams params) {
        CustomSuccessResponse result = loginOAuth(params);
        return new ResponseEntity<>(result, result.getSuccess().getHttpStatus());
    }

    @PostMapping("/naver")
    public ResponseEntity<CustomSuccessResponse> loginNaver(@RequestBody NaverLoginParams params) {
        CustomSuccessResponse result = loginOAuth(params);
        return new ResponseEntity<>(result, result.getSuccess().getHttpStatus());
    }
    @PostMapping("/google")
    public ResponseEntity<CustomSuccessResponse> loginGoogle(@RequestBody GoogleLoginParams params) {
        CustomSuccessResponse result = loginOAuth(params);
        return new ResponseEntity<>(result, result.getSuccess().getHttpStatus());
    }

    private CustomSuccessResponse loginOAuth(OAuthLoginParams params) {
        AuthResult<?> result = oAuthLoginService.loginOrCreateTempAccount(params);
        return new CustomSuccessResponse.SuccessDetail()
                .message(result.getMessage())
                .httpStatus(result.getHttpStatus())
                .responseData(result.getResponse())
                .build();
    }


    @PostMapping("/oauth")
    public ResponseEntity<CustomSuccessResponse> oAuthSignUp(@RequestBody @Valid OAuthSignUpDto oAuthSignUpDto) {
        oAuthLoginService.signUp(oAuthSignUpDto);
        CustomSuccessResponse signUpResponse = createSignUpResponse();
        return new ResponseEntity<>(signUpResponse, signUpResponse.getSuccess().getHttpStatus());
    }

    private CustomSuccessResponse createSignUpResponse(){
        return new CustomSuccessResponse.SuccessDetail()
                .message("회원가입 완료")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/tt")
    public CustomSuccessResponse tt(@Parameter(schema = @Schema(type = "string", example = "카카오")) @RequestParam OAuthProvider provider){
        return new CustomSuccessResponse.SuccessDetail()
                .responseData(provider).build();
    }
    @GetMapping("/ttt")
    public CustomSuccessResponse ttt(@Parameter(schema = @Schema(type = "string", example = "카카오")) @RequestParam Gender gender){
        return new CustomSuccessResponse.SuccessDetail()
                .responseData(gender).build();
    }
    @GetMapping("/tttt")
    public CustomSuccessResponse tttt(@Parameter(schema = @Schema(type = "string", example = "카카오")) @RequestParam RolesEnum role){
        return new CustomSuccessResponse.SuccessDetail()
                .responseData(role).build();
    }



}
