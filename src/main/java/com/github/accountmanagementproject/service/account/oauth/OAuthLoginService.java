package com.github.accountmanagementproject.service.account.oauth;

import com.github.accountmanagementproject.common.converter.mapper.UserMapper;
import com.github.accountmanagementproject.common.exceptions.CustomBadRequestException;
import com.github.accountmanagementproject.common.exceptions.CustomNotFoundException;
import com.github.accountmanagementproject.common.exceptions.CustomServerException;
import com.github.accountmanagementproject.common.myenum.Gender;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import com.github.accountmanagementproject.common.myenum.RoleEnum;
import com.github.accountmanagementproject.common.security.provider.CustomAuthenticationProvider;
import com.github.accountmanagementproject.common.security.userdetails.CustomUserDetails;
import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.config.security.JwtProvider;
import com.github.accountmanagementproject.repository.account.role.Role;
import com.github.accountmanagementproject.repository.account.socialid.SocialId;
import com.github.accountmanagementproject.repository.account.socialid.SocialIdPk;
import com.github.accountmanagementproject.repository.account.socialid.SocialIdRepository;
import com.github.accountmanagementproject.repository.account.user.MyUser;
import com.github.accountmanagementproject.repository.account.user.MyUserRepository;
import com.github.accountmanagementproject.web.dto.account.auth.response.TokenDto;
import com.github.accountmanagementproject.web.dto.account.oauth.request.OAuthLoginParams;
import com.github.accountmanagementproject.web.dto.account.oauth.response.AuthResult;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthSignUpDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MyUserRepository myUsersRepository;
    private final SocialIdRepository socialIdsRepository;
    private final OAuthClientManager oAuthClientManager;
    private final JwtProvider jwtProvider;
    private final OAuthCodeManager oAuthCodeManager;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public String getAuthorizationUrl(OAuthProvider oAuthProvider, String redirectUri) {
        return oAuthCodeManager.getAuthorizationUrl(oAuthProvider, redirectUri);
    }


    @Transactional
    public AuthResult loginOrCreateTempAccount(OAuthLoginParams params) {
        //소셜 서버에 요청해서 사용자 정보 받아오기
        OAuthUserInfo oAuthUserInfo = oAuthClientManager.request(params);
        SocialIdPk socialIdPk = SocialIdPk.of(oAuthUserInfo.getSocialId(), oAuthUserInfo.getOAuthProvider());
        //DB 에서 소셜 사용자 정보 찾기
        MyUser requestUser = myUsersRepository.findBySocialIdPkOrUserEmail(socialIdPk, oAuthUserInfo.getEmail())
                .orElseGet(() -> processTempSignUp(oAuthUserInfo));//없으면 임시 회원가입 진행
        //필요에 따라 유저 정보에 소셜 ID, 프로필이미지 설정
        requestUserSetSocialId(requestUser, socialIdPk);
        requestUser.updateProfileImgFromOAuthInfo(oAuthUserInfo.getProfileImg());


        //로그인 또는 회원가입 응답 생성
        return requestUser.isEnabled() ? createOAuthLoginResponse(requestUser) : createOAuthSignUpResponse(oAuthUserInfo);
    }

    private void requestUserSetSocialId(MyUser requestUser, SocialIdPk socialIdPk) {
        boolean hasSocialIdPk = requestUser.getSocialIds() != null &&
                requestUser.getSocialIds().stream()
                        .map(SocialId::getSocialIdPk)
                        .anyMatch(pk -> pk.equals(socialIdPk));
        if (hasSocialIdPk) return;

        SocialId newSocialId = SocialId.ofSocialIdPkAndMyUser(socialIdPk, requestUser);
        requestUser.addSocialId(newSocialId);
    }

    private AuthResult createOAuthSignUpResponse(OAuthUserInfo oAuthUserInfo) {
        return AuthResult.builder()
                .response(UserMapper.INSTANCE.oAuthUserInfoToOAuthSignUpDto(oAuthUserInfo))
                .message("임시 계정 생성")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
    //계정의 유효성 여부를 검사하고 SecurityContextHolder에 인증 정보를 설정합니다.
    //로그인 성공 이벤트도 발행
    private void securityOAuthSuccessVerify(MyUser myUser){
        CustomUserDetails userDetails = UserMapper.INSTANCE.myUserToCustomUserDetails(myUser);
        customAuthenticationProvider.oauthAuthenticate(userDetails);
    }

    private AuthResult createOAuthLoginResponse(MyUser myUser) {
        securityOAuthSuccessVerify(myUser);
        return AuthResult.builder()
                .response(createTokenAndSave(myUser))
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK)
                .build();
    }


    private TokenDto createTokenAndSave(MyUser myUser) {
        String roles = myUser.getRoles().stream().map(role -> role.getName().name())
                .collect(Collectors.joining(","));
        //토큰 생성
        String accessToken = jwtProvider.createNewAccessToken(myUser.getEmail(), roles);
        String refreshToken = jwtProvider.createNewRefreshToken();
        try {
//            myUser.loginValueSetting(false);
            return jwtProvider.saveRefreshTokenAndCreateTokenDto(accessToken, refreshToken, Duration.ofMinutes(3));
        } catch (RedisConnectionFailureException e) {
            throw CustomServerException.of()
                    .systemMessage(e.getMessage())
                    .customMessage("Redis 서버 연결 실패")
                    .build();
        }
    }

    private MyUser processTempSignUp(OAuthUserInfo oAuthUserInfo) {
        MyUser newUser = UserMapper.INSTANCE.oAuthInfoResponseToMyUser(oAuthUserInfo);
        newUser.setRoles(Set.of(Role.fromName(RoleEnum.ROLE_USER)));
        return myUsersRepository.save(newUser);
    }


    private SocialId validationAndFindSocialId(OAuthSignUpDto oAuthSignUpDto) {
        SocialId socialId = socialIdsRepository.findBySocialIdPkJoinMyUser(SocialIdPk.of(oAuthSignUpDto.getSocialId(), oAuthSignUpDto.getProvider()))
                .orElseThrow(() -> CustomNotFoundException.of()
                        .customMessage("임시 계정이 존재하지 않습니다.")
                        .request("oAuthSignUpDto")
                        .systemMessage("NotFoundException")
                        .build());
        if (socialId.getMyUser().isEnabled())
            throw CustomBadRequestException.of()
                    .customMessage("이미 가입된 계정입니다.")
                    .request("oAuthSignUpDto")
                    .build();
        return socialId;
    }

    @Transactional
    public void signUp(OAuthSignUpDto oAuthSignUpDto) {
        SocialId socialId = validationAndFindSocialId(oAuthSignUpDto);
        try {
            socialId.socialConnectSetting();
            socialId.getMyUser().oAuthSignUpSetting(oAuthSignUpDto);
        } catch (DateTimeException e) {
            throw CustomBadRequestException.of()
                    .systemMessage(e.getMessage())
                    .customMessage("호환되지 않는 날짜 형식 (ex. yyyy-M-d)")
                    .request(oAuthSignUpDto.getDateOfBirth())
                    .build();
        }
    }

}
