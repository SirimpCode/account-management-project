package com.github.accountmanagementproject.service.authAccount.oauth;

import com.github.accountmanagementproject.config.client.dto.userInfo.OAuthUserInfo;
import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.config.security.JwtProvider;
import com.github.accountmanagementproject.repository.account.socialIds.SocialId;
import com.github.accountmanagementproject.repository.account.socialIds.SocialIdPk;
import com.github.accountmanagementproject.repository.account.socialIds.SocialIdsJpa;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.service.customExceptions.CustomNotFoundException;
import com.github.accountmanagementproject.service.customExceptions.CustomServerException;
import com.github.accountmanagementproject.service.mappers.UserMapper;
import com.github.accountmanagementproject.web.dto.accountAuth.TokenDto;
import com.github.accountmanagementproject.web.dto.accountAuth.oauth.request.OAuthLoginParams;
import com.github.accountmanagementproject.web.dto.accountAuth.oauth.response.AuthResult;
import com.github.accountmanagementproject.web.dto.accountAuth.oauth.response.OAuthSignUpDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MyUsersJpa myUsersJpa;
    private final SocialIdsJpa socialIdsJpa;
    private final AccountConfig accountConfig;
    private final OAuthClientManager oAuthClientManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResult<?> loginOrCreateTempAccount(OAuthLoginParams params) {

        //소셜 서버에 요청해서 사용자 정보 받아오기
        OAuthUserInfo oAuthUserInfo = oAuthClientManager.request(params);
        //DB에서 사용자 정보 찾기
        Optional<MyUser> myUserOptional = myUsersJpa.findBySocialIdPk(new SocialIdPk(oAuthUserInfo.getSocialId(),oAuthUserInfo.getOAuthProvider()));
        //DB에 사용자 정보가 없으면 임시 회원가입 진행
        MyUser myUser = myUserOptional.orElseGet(() -> processSignUp(oAuthUserInfo));
        //로그인 또는 회원가입 응답 생성
        return myUserOptional.isPresent() & !myUser.isDisabled() ? createOAuthLoginResponse(myUser) : createOAuthSignUpResponse(oAuthUserInfo);

    }

    private AuthResult<OAuthSignUpDto> createOAuthSignUpResponse(OAuthUserInfo oAuthUserInfo) {
        return AuthResult.<OAuthSignUpDto>builder()
                .response( UserMapper.INSTANCE.oAuthUserInfoToOAuthSignUpDto(oAuthUserInfo) )
                .message("임시 계정 생성")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
    private AuthResult<TokenDto> createOAuthLoginResponse(MyUser myUser) {
        return AuthResult.<TokenDto>builder()
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
            myUser.loginValueSetting(false);
            return jwtProvider.saveRefreshTokenAndCreateTokenDto(accessToken, refreshToken, Duration.ofMinutes(3));
        } catch (RedisConnectionFailureException e) {
            throw new CustomServerException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("Redis 서버 연결 실패")
                    .build();
        }
    }

    private MyUser processSignUp(OAuthUserInfo oAuthUserInfo) {
        MyUser newUser = UserMapper.INSTANCE.oAuthInfoResponseToMyUser(oAuthUserInfo);
        newUser.setRoles(Set.of(accountConfig.getNormalUserRole()));
        return myUsersJpa.save(newUser);
    }

    @Transactional
    public void signUp(OAuthSignUpDto oAuthSignUpDto) {
        SocialId socialId = socialIdsJpa.findBySocialIdPkJoinMyUser(new SocialIdPk(oAuthSignUpDto.getSocialId(), oAuthSignUpDto.getProvider()))
                .orElseThrow(() -> new CustomNotFoundException.ExceptionBuilder()
                        .customMessage("임시 계정이 존재하지 않습니다.")
                        .request("oAuthSignUpDto")
                        .systemMessage("NotFoundException")
                        .build());
        try {
            socialId.socialConnectSetting();
            socialId.getMyUser().oAuthSignUpSetting(oAuthSignUpDto);
        } catch (DateTimeException e) {
            throw new CustomBadRequestException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("호환되지 않는 날짜 형식 (ex. yyyy-M-d)")
                    .request(oAuthSignUpDto.getDateOfBirth())
                    .build();
        }
    }
}
