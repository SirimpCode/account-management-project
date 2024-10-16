//package com.github.accountmanagementproject.web.controller.authAccount;
//
//import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/auth/social")
//@RequiredArgsConstructor
//public class SocialController {
//    private final SocialAuthenticationService socialAuthenticationService;
//
//    @PostMapping("/kakao")
//    public CustomSuccessResponse loginKakao(@RequestBody KakaoLoginParams params, HttpServletResponse httpServletResponse) {
//
//        List<Object> tokenAndResponse = socialSignUpService.login(params);
//        httpServletResponse.setHeader("Token", (String) tokenAndResponse.get(0));
//        return (ResponseDto) tokenAndResponse.get(1);
//
//
//    }
//    @PostMapping("/connect")
//    public ResponseEntity<ResponseDto> connectAccount(
//            @RequestParam(name = "is-connect") boolean isConnect,
//            @RequestParam(name = "social-id") Long socialId){
//        return socialSignUpService.connectAccount(isConnect, socialId);
//    }
//    @PostMapping("/sign-up")
//    public ResponseEntity<ResponseDto> socialSignUp(
//            @RequestParam(name = "is-sign-up") boolean isSignUp,
//            @RequestParam(name = "social-id") Long socialId,
//            @RequestBody(required = false) SignUpRequest signUpRequest){
//        return socialSignUpService.socialSignUpFix(isSignUp, socialId, signUpRequest);
//    }
////    @PostMapping("/naver")
////    public String loginNaver(@RequestBody NaverLoginParams params) {
////        return socialSignUpService.login(params);
////    }
//}
