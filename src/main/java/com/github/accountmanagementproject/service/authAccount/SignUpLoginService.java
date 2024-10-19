package com.github.accountmanagementproject.service.authAccount;

import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.config.security.JwtProvider;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomBadCredentialsException;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.service.customExceptions.CustomServerException;
import com.github.accountmanagementproject.service.mappers.UserMapper;
import com.github.accountmanagementproject.web.dto.accountAuth.AccountInfoDto;
import com.github.accountmanagementproject.web.dto.accountAuth.LoginRequest;
import com.github.accountmanagementproject.web.dto.accountAuth.TokenDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignUpLoginService {

    private final MyUsersJpa myUsersJpa;
    private final AccountConfig accountConfig;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public void signUp(AccountInfoDto signUpRequest) {
        //비번 암호화
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        //세이브 실행하면서 중복값 발생시 발생되는 익셉션 예외처리
        try {
            MyUser signUpMyUser = UserMapper.INSTANCE.accountDtoToMyUser(signUpRequest);
            signUpMyUser.setRoles(Set.of(accountConfig.getNormalUserRole()));
            myUsersJpa.save(signUpMyUser);
        }catch (DateTimeException e){
            throw new CustomBadRequestException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("호환되지 않는 날짜 형식 (ex. yyyy-M-d)")
                    .request(signUpRequest.getDateOfBirth())
                    .build();
        }
    }

    public TokenDto loginResponseToken(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(loginRequest.toAuthentication());

        String roles = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        String accessToken = jwtProvider.createNewAccessToken(authentication.getName(), roles);
        String refreshToken = jwtProvider.createNewRefreshToken();
        try {
            return jwtProvider.saveRefreshTokenAndCreateTokenDto(accessToken, refreshToken, Duration.ofMinutes(3));
        } catch (RedisConnectionFailureException e) {
            throw new CustomServerException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("Redis 서버 연결 실패")
                    .build();
        }
    }

    public TokenDto refreshTokenByTokenDto(TokenDto tokenDto) {
        try{
            return jwtProvider.tokenRefresh(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        }catch (RedisConnectionFailureException e){
            throw new CustomServerException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("Redis 서버 연결 실패")
                    .request(tokenDto)
                    .build();
        }catch (ExpiredJwtException | NoSuchElementException e){
            throw new CustomBadCredentialsException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage(e instanceof ExpiredJwtException ? "리프레시 토큰 만료" : "재발급 받을 수 없는 액세스 토큰")
                    .request(tokenDto)
                    .build();
        }

    }

    public void errorTest() {
        MyUser user = myUsersJpa.findByEmail("abc@abc.com").orElseThrow();
        myUsersJpa.delete(user);
    }
}
