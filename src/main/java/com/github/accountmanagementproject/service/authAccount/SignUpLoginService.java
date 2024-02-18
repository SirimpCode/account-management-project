package com.github.accountmanagementproject.service.authAccount;

import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.config.security.JwtTokenConfig;
import com.github.accountmanagementproject.repository.account.users.User;
import com.github.accountmanagementproject.repository.account.users.UsersJpa;
import com.github.accountmanagementproject.service.MakeResponseService;
import com.github.accountmanagementproject.service.authAccount.userDetailsService.CustomUserDetailService;
import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.service.mappers.UserMapper;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import com.github.accountmanagementproject.web.dto.account.JwtTokenDTO;
import com.github.accountmanagementproject.web.dto.account.LoginRequest;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.LazyInitializationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SignUpLoginService {
    private final MakeResponseService makeResponseService;

    private final UsersJpa usersJpa;
    private final AccountConfig accountConfig;
    private final JwtTokenConfig jwtTokenConfig;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional(transactionManager = "jtm")
    public CustomSuccessResponse signUp(AccountDto signUpRequest) {

        //필수입력값 요구사항 확인, 기본 프사설정 로직
        accountConfig.signUpChecker(signUpRequest);

        //비번 암호화
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        //매퍼로 엔티티변환 후 롤 세팅
        User signUpUser = UserMapper.INSTANCE.AccountDtoToUserEntity(signUpRequest);
        signUpUser.getRoles().add(accountConfig.getNormalUserRole());



        //세이브 실행하면서 중복값 발생시 발생되는 익셉션 예외처리
        try {
            usersJpa.save(signUpUser);
        }catch (DataIntegrityViolationException e){
            Map<String, String> duplicateResponse = new LinkedHashMap<>();
            duplicateResponse.put("email", signUpRequest.getEmail());
            duplicateResponse.put("phoneNumber", signUpRequest.getPhoneNumber());
            duplicateResponse.put("nickname", signUpRequest.getNickname());
            throw new DuplicateKeyException(e.getMessage(),"이메일, 핸드폰 번호, 닉네임 세 값중 중복 값이 있습니다. 중복 확인을 해주세요.", duplicateResponse);
        }


        return new CustomSuccessResponse(
                makeResponseService.makeSuccessDetail(HttpStatus.CREATED, "회원가입에 성공 하였습니다.")
        );
    }

    public List<Object> loginResponseToken(LoginRequest loginRequest){
        String emailOrPhoneNumber = loginRequest.getEmailOrPhoneNumber();
        String password = loginRequest.getPassword();

        if(emailOrPhoneNumber==null||password==null){
            throw new CustomBadRequestException("로그인 요청값이 전달되지 않았습니다.", loginRequest);
        }
        try{
        Authentication authentication = authenticationManager.authenticate(
                //내가 만든 loadUserByUsername 메서드로 인증진행
                new UsernamePasswordAuthenticationToken(emailOrPhoneNumber,password)
        );

        JwtTokenDTO jwtTokenDTO = jwtTokenConfig.createToken(authentication);



        return Arrays.asList(jwtTokenDTO.getGrantType()+" "+jwtTokenDTO.getAccessToken(),new CustomSuccessResponse(
                makeResponseService.makeSuccessDetail(HttpStatus.OK, "로그인에 성공 하였습니다.", jwtTokenDTO)
        ));
        }
        catch (BadCredentialsException e){
            throw new CustomBadCredentialsException(e.getMessage(), "비밀번호가 틀렸습니다.", loginRequest.getPassword());
        }catch (LazyInitializationException e){
            throw new CustomNotAcceptException(e.getMessage(), "db 조회 실패", loginRequest.getEmailOrPhoneNumber());
        }
    }

    public String securityTest(Integer userId) {
        CustomUserDetailService customUserDetailService = new CustomUserDetailService(usersJpa);
        User user = usersJpa.findById(userId).orElseThrow(()->new CustomNotFoundException("으악",userId));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getPhoneNumber());
        return userDetails.getAuthorities().toString();
    }

    public String tokenTest(String authorization) {
        if(authorization==null||!authorization.startsWith("Bearer")) return "토큰똑바로줘라";
        Authentication authentication = jwtTokenConfig.getAuthentication(authorization.substring(7));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Object customUserDetails = authentication.getPrincipal();


        return authorization.substring(7);
    }
}
