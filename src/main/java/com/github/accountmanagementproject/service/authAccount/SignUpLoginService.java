package com.github.accountmanagementproject.service.authAccount;

import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.config.security.JwtProvider;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.service.authAccount.userDetailsService.CustomUserDetailService;
import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.service.mappers.UserMapper;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import com.github.accountmanagementproject.web.dto.account.JwtDto;
import com.github.accountmanagementproject.web.dto.account.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SignUpLoginService {

    private final MyUsersJpa myUsersJpa;
    private final AccountConfig accountConfig;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;


    @Transactional
    public void signUp(AccountDto signUpRequest) {

        //필수입력값 요구사항 확인, 기본 프사설정 로직
        accountConfig.signUpChecker(signUpRequest);

        //비번 암호화
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));


        //매퍼로 엔티티변환 후 롤 세팅



        //세이브 실행하면서 중복값 발생시 발생되는 익셉션 예외처리
        try {

            MyUser signUpMyUser = UserMapper.INSTANCE.accountDtoToMyUser(signUpRequest);
            signUpMyUser.getRoles().add(accountConfig.getNormalUserRole());
            myUsersJpa.save(signUpMyUser);
        }catch (DataIntegrityViolationException e){
            Map<String, String> duplicateResponse = new LinkedHashMap<>();
            duplicateResponse.put("email", signUpRequest.getEmail());
            duplicateResponse.put("phoneNumber", signUpRequest.getPhoneNumber());
            duplicateResponse.put("nickname", signUpRequest.getNickname());
            throw new DuplicateKeyException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("이메일, 핸드폰 번호, 닉네임 세 값중 중복 값 발생")
                    .request(duplicateResponse)
                    .build();
        }catch (DateTimeException e){
            throw new CustomBadRequestException.ExceptionBuilder()
                    .systemMessage(e.getMessage())
                    .customMessage("호환되지 않는 날짜 형식 (ex. yyyy-M-d)")
                    .request(signUpRequest.getDateOfBirth())
                    .build();
        }
    }

    public JwtDto loginResponseToken(LoginRequest loginRequest) {
            //내가 만든 loadUserByUsername 메서드로 인증진행
            //db에서 객체를 조회하고 db에 있는 패스워드와 식별자로 UserDetails 를 생성
            //그 후 두번째로들어간 password와 userdetails의 password가 match하는지 확인
            Authentication authentication = authenticationManager.authenticate(loginRequest.toAuthentication());
            return jwtProvider.createToken(authentication);
//        catch (AccountStatusException e){
//            String[] systemMessage = e.getMessage().split(",");
//            if(e instanceof LockedException){
//                Duration remaining = Duration.between(LocalDateTime.now().plusMinutes(5), LocalDateTime.parse(systemMessage[1]));
//                long minutes = remaining.toMinutes();
//                long seconds = remaining.minusMinutes(minutes).getSeconds();
//                throw new CustomBadRequestException(e.getMessage(), minutes+"분"+seconds+"초", loginRequest.getEmailOrPhoneNumber());
//            } else if (e instanceof DisabledException) {
//                String withdrawalDate = LocalDateTime.parse(systemMessage[1]).format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"));
//                throw new CustomBadRequestException(e.getMessage(),withdrawalDate,loginRequest.getEmailOrPhoneNumber());
//            }
//            throw new CustomBadRequestException(e.getMessage(), "로그인 실패", loginRequest.getEmailOrPhoneNumber());
//        }
    }

    public JwtDto refreshToken(JwtDto jwtDto){
        String accessToken = jwtDto.getAccessToken();
        String refreshToken = jwtDto.getRefreshToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        return jwtProvider.createToken(authentication);
    }

}
