package com.github.accountmanagementproject.web.controller.account.auth;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.service.account.oauth.OAuthLoginService;
import com.github.accountmanagementproject.service.account.oauth.OAuthProviderService;
import com.github.accountmanagementproject.web.dto.account.oauth.request.*;
import com.github.accountmanagementproject.web.dto.account.oauth.response.AuthResult;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthSignUpDto;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController implements OAuthControllerDocs {
    private final OAuthProviderService oAuthProviderService;
    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/{provider}/test")
    public ResponseEntity<Void> requestOAuthCodeUrlRedirect(@PathVariable OAuthProvider provider, HttpServletRequest httpServletRequest) {
        System.out.println("컨트롤러단 테스트 1");
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, oAuthProviderService.getOAuthLoginPageUrl(provider, httpServletRequest))
                .build();
    }


    @Override
    @GetMapping("/{provider}/callback")//백에서 Post 요청 api 메서드 호출해서 처리
    public ResponseEntity<CustomSuccessResponse<AuthResult>> oAuthRequest(@PathVariable OAuthProvider provider, HttpServletRequest httpServletRequest) {
        System.out.println("컨트롤러단 테스트 2 " + httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName());

        OAuthCodeParams codeParams = createCodeParams(httpServletRequest);
        return test(codeParams, provider);
    }

    private ResponseEntity<CustomSuccessResponse<AuthResult>> test(OAuthCodeParams params, OAuthProvider provider) {
        return switch (provider) {
            case KAKAO -> loginKakao(KakaoLoginParams.of(params.getCode()));
            case NAVER -> loginNaver(NaverLoginParams.of(params.getCode(), params.getState()));
            case GOOGLE -> loginGoogle(GoogleLoginParams.of(params.getCode(), params.getRedirectUri()));
            case GITHUB -> loginGithub(GithubLoginParams.of(params.getCode()));
        };
    }


    private OAuthCodeParams createCodeParams(HttpServletRequest request) {
        return OAuthCodeParams.of(
                request.getParameter("code"),
                request.getParameter("state"),
                request.getRequestURL().toString()
        );
    }

    @GetMapping("/{provider}")
    public CustomSuccessResponse<String> getProviderAuthUrl(@PathVariable OAuthProvider provider, @RequestParam String redirectUri) {
        System.out.println("실행 몇번되나 테스트");
        return CustomSuccessResponse.ofOk("인증 URL 생성 성공", oAuthLoginService.getAuthorizationUrl(provider, redirectUri));
    }

    @PostMapping("/kakao")
    public ResponseEntity<CustomSuccessResponse<AuthResult>> loginKakao(@RequestBody KakaoLoginParams params) {
        return loginOAuth(params);
    }

    @PostMapping("/naver")
    public ResponseEntity<CustomSuccessResponse<AuthResult>> loginNaver(@RequestBody NaverLoginParams params) {
        return loginOAuth(params);
    }

    @PostMapping("/google")
    public ResponseEntity<CustomSuccessResponse<AuthResult>> loginGoogle(@RequestBody GoogleLoginParams params) {
        return loginOAuth(params);
    }

    @PostMapping("/github")
    public ResponseEntity<CustomSuccessResponse<AuthResult>> loginGithub(@RequestBody GithubLoginParams params) {
        return loginOAuth(params);
    }

    private ResponseEntity<CustomSuccessResponse<AuthResult>> loginOAuth(OAuthLoginParams params) {
        AuthResult result = oAuthLoginService.loginOrCreateTempAccount(params);
        CustomSuccessResponse<AuthResult> response = CustomSuccessResponse
                .of(result.getHttpStatus(), "로그인 성공", result);

        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);

    }


    @PostMapping("/sign-up")
    public ResponseEntity<CustomSuccessResponse<Void>> oAuthSignUp(@RequestBody @Valid OAuthSignUpDto oAuthSignUpDto) {
        oAuthLoginService.signUp(oAuthSignUpDto);
        CustomSuccessResponse<Void> signUpResponse = CustomSuccessResponse
                .emptyData(HttpStatus.CREATED, "회원가입 완료");
        return ResponseEntity
                .status(signUpResponse.getHttpStatus())
                .body(signUpResponse);
    }
}



