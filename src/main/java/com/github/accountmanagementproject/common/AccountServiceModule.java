package com.github.accountmanagementproject.common;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersRepository;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.common.exceptions.CustomBadRequestException;
import com.github.accountmanagementproject.common.exceptions.CustomNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountServiceModule {
    private final MyUsersRepository myUsersRepository;
    private final EntityManager entityManager;
    private final HttpSession httpSession;


    private static Role normalUserRole;
    private static Role adminUserRole;

//    @PostConstruct
//    public void init(){
//        normalUserRole = rolesJpa.findByName("ROLE_USER");
//    }

    public Role getNormalUserRole() {
        if (normalUserRole == null) {
            normalUserRole = new Role(2);
        }
        return normalUserRole;
    }

    public Role getAdminUserRole() {
        if (adminUserRole == null) {
            adminUserRole = new Role(1);
        }
        return adminUserRole;
    }


    public MyUser findMyUserFetchJoin(String emailOrPhoneNumber) {
        if (emailOrPhoneNumber.matches("01\\d{9}")) {
            return myUsersRepository.findByPhoneNumberJoin(emailOrPhoneNumber).orElseThrow(() ->
                    CustomNotFoundException.of()
                            .customMessage("가입되지 않은 핸드폰 번호")
                            .request(emailOrPhoneNumber)
                            .build());
        } else if (emailOrPhoneNumber.matches(".+@.+\\..+")) {
            return myUsersRepository.findByEmailJoin(emailOrPhoneNumber).orElseThrow(() ->
                    CustomNotFoundException.of()
                            .customMessage("가입되지 않은 이메일")
                            .request(emailOrPhoneNumber)
                            .build());
        } else throw CustomBadRequestException.of()
                .customMessage("잘못 입력된 식별자")
                .request(emailOrPhoneNumber)
                .build();
    }

    public MyUser findMyUser(String emailOrPhoneNumber) {
        if (emailOrPhoneNumber.matches("01\\d{9}")) {
            return myUsersRepository.findByPhoneNumber(emailOrPhoneNumber).orElseThrow(() ->
                    CustomNotFoundException.of()
                            .customMessage("가입되지 않은 핸드폰 번호")
                            .request(emailOrPhoneNumber)
                            .build());
        } else if (emailOrPhoneNumber.matches(".+@.+\\..+")) {
            return myUsersRepository.findByEmail(emailOrPhoneNumber).orElseThrow(() ->
                    CustomNotFoundException.of()
                            .customMessage("가입되지 않은 이메일")
                            .request(emailOrPhoneNumber)
                            .build());
        } else throw CustomBadRequestException.of()
                .customMessage("잘못 입력된 식별자")
                .request(emailOrPhoneNumber)
                .build();
    }


    //회원가입시 사용되는 로직 모듈화
//    public void signUpChecker(AccountDto request){
//        String email = request.getEmail();
//        String nickname = request.getNickname();
//        String phoneNumber = request.getPhoneNumber();
//        String password = request.getPassword();
//
//        if(request.requiredNull()){
//            throw new CustomBadRequestException.ExceptionBuilder()
//                    .customMessage("필수 입력값 누락")
//                    .request(Map.of(
//                            "email", request.getEmail(),
//                            "phoneNumber", request.getPhoneNumber(),
//                            "nickname", request.getNickname(),
//                            "password", request.getPassword(),
//                            "passwordConfirm", request.getPasswordConfirm()
//                    ))
//                    .build();
//        } else if(request.badEmailValue()){
//            throw new CustomBadRequestException.ExceptionBuilder()
//                    .customMessage("잘못된 이메일")
//                    .request(email)
//                    .build();
//        } else if (request.badPhoneNumValue()) {
//            throw new CustomBadRequestException.ExceptionBuilder()
//                    .customMessage("잘못된 핸드폰 번호")
//                    .request(phoneNumber)
//                    .build();
//        } else if (request.badNicknameValue()){
//            throw new CustomBadRequestException.ExceptionBuilder()
//                    .customMessage("핸드폰 번호 형식으로 작성된 닉네임")
//                    .request(nickname)
//                    .build();
//        } else if(request.badPasswordValue()){
//            throw new CustomBadRequestException.ExceptionBuilder()
//                    .customMessage("최소 조건을 충족하지 못한 비밀번호")
//                    .request(password)
//                    .build();
//        } else if (request.differentPasswordConfirm()) {
//            throw new CustomBadRequestException.ExceptionBuilder()
//                    .customMessage("서로 다른 비밀번호와 비밀번호 확인")
//                    .request(Map.of(
//                            "password", password,
//                            "passwordConfirm", request.getPasswordConfirm()
//                    ))
//                    .build();
//        }
//        //성별 별 기본 프사 설정
//        request.setDefaultProfileImg();
//    }


    @Transactional
    public MyUser failureCounting(MyUser failUser) {
        failUser.loginValueSetting(true);
        myUsersRepository.updateFailureCountByEmail(failUser);
        return failUser;
    }

    @Transactional
    public void loginSuccessEvent(MyUser sucUser) {
        sucUser.loginValueSetting(false);
        myUsersRepository.updateFailureCountByEmail(sucUser);
    }


}
