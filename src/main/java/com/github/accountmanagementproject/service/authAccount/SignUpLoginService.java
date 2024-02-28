package com.github.accountmanagementproject.service.authAccount;

import com.github.accountmanagementproject.config.security.AccountConfig;
import com.github.accountmanagementproject.config.security.JwtTokenConfig;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.service.authAccount.userDetailsService.CustomUserDetailService;
import com.github.accountmanagementproject.service.customExceptions.*;
import com.github.accountmanagementproject.service.mappers.UserMapper;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import com.github.accountmanagementproject.web.dto.account.JwtToken;
import com.github.accountmanagementproject.web.dto.account.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SignUpLoginService {

    private final MyUsersJpa myUsersJpa;
    private final AccountConfig accountConfig;
    private final JwtTokenConfig jwtTokenConfig;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


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
            throw new DuplicateKeyException(e.getMessage(),"이메일, 핸드폰 번호, 닉네임 세 값중 중복 값 발생", duplicateResponse);
        }catch (DateTimeException e){
            throw new CustomBadRequestException(e.getMessage(), "호환되지 않는 날짜 형식 (ex. yyyy-MM-dd)", signUpRequest.getDateOfBirth());
        }
    }

    public JwtToken loginResponseToken(LoginRequest loginRequest){
        String emailOrPhoneNumber = loginRequest.getEmailOrPhoneNumber();
        String password = loginRequest.getPassword();

//        if(emailOrPhoneNumber==null||password==null){
//            throw new CustomBadRequestException("로그인 요청값이 전달되지 않았습니다.", loginRequest);
//        }
        try{
        Authentication authentication = authenticationManager.authenticate(
                //내가 만든 loadUserByUsername 메서드로 인증진행
                //db에서 객체를 조회하고 db에 있는 패스워드와 식별자로 UserDetails 를 생성
                //그 후 두번째로들어간 password와 userdetails의 password가 match하는지 확인
                new UsernamePasswordAuthenticationToken(emailOrPhoneNumber,password)
        );


        return jwtTokenConfig.createToken(authentication);
        }
        catch (BadCredentialsException e){
            throw new CustomBadCredentialsException(e.getMessage(), "비밀번호 오류", loginRequest.getPassword());
        }
    }

    @Transactional
    public String securityTest(Integer userId) {
        CustomUserDetailService customUserDetailService = new CustomUserDetailService(myUsersJpa);
        MyUser myUser = myUsersJpa.findById(userId).orElseThrow(()->new CustomNotFoundException("으악",userId));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(myUser.getPhoneNumber());
        List<String> s = myUser.getRoles().stream().map(rl->rl.getName().name()).toList();
        System.out.println(s);
//        myUsersJpa.delete(myUser);
        Set<Role> ss = myUser.getRoles();

        return s.toString();
    }

    public String tokenTest(String authorization) {
        if(authorization==null||!authorization.startsWith("Bearer")) return "토큰똑바로줘라";
//        Authentication authentication = jwtTokenConfig.getAuthentication(authorization.substring(7));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Object customUserDetails = authentication.getPrincipal();


        return authorization.substring(7);
    }
}
