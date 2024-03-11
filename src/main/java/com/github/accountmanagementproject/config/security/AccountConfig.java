package com.github.accountmanagementproject.config.security;

import com.github.accountmanagementproject.config.security.event.TestEvent;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.repository.account.users.roles.RolesJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.service.customExceptions.CustomNotFoundException;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccountConfig {
    private final RolesJpa rolesJpa;
    private final MyUsersJpa myUsersJpa;

    //일반유저롤 자주 호출되서 싱글톤적용
    private static Role normalUserRole;

    private static Role adminUserRole;

//    @PostConstruct
//    public void init(){
//        normalUserRole = rolesJpa.findByName("ROLE_USER");
//    }

    public Role getNormalUserRole(){
        if(normalUserRole == null){
            normalUserRole = rolesJpa.findByName(RolesEnum.ROLE_USER);
        }
        return normalUserRole;
    }
    public Role getAdminUserRole(){
        if(adminUserRole == null){
            adminUserRole = rolesJpa.findByName(RolesEnum.ROLE_ADMIN);
        }
        return adminUserRole;
    }


    public MyUser findMyUserFetchJoin(String emailOrPhoneNumber){
        if(emailOrPhoneNumber.matches("01\\d{9}")){
            return myUsersJpa.findByPhoneNumberJoin(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException.ExceptionBuilder()
                            .customMessage("가입되지 않은 핸드폰 번호")
                            .request(emailOrPhoneNumber)
                            .build());
        }else if (emailOrPhoneNumber.matches(".+@.+\\..+")){
            return myUsersJpa.findByEmailJoin(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException.ExceptionBuilder()
                            .customMessage("가입되지 않은 이메일")
                            .request(emailOrPhoneNumber)
                            .build());
        }else throw new CustomBadRequestException.ExceptionBuilder()
                .customMessage("잘못 입력된 식별자")
                .request(emailOrPhoneNumber)
                .build();
    }
    public MyUser findMyUser(String emailOrPhoneNumber){
        if(emailOrPhoneNumber.matches("01\\d{9}")){
            return myUsersJpa.findByPhoneNumber(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException.ExceptionBuilder()
                            .customMessage("가입되지 않은 핸드폰 번호")
                            .request(emailOrPhoneNumber)
                            .build());
        }else if (emailOrPhoneNumber.matches(".+@.+\\..+")){
            return myUsersJpa.findByEmail(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException.ExceptionBuilder()
                            .customMessage("가입되지 않은 이메일")
                            .request(emailOrPhoneNumber)
                            .build());
        }else throw new CustomBadRequestException.ExceptionBuilder()
                .customMessage("잘못 입력된 식별자")
                .request(emailOrPhoneNumber)
                .build();
    }




    //회원가입시 사용되는 로직 모듈화
    public void signUpChecker(AccountDto request){
        String email = request.getEmail();
        String nickname = request.getNickname();
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();

        if(request.requiredNull()){
            Map<String, String> nullResponse = new LinkedHashMap<>();
            nullResponse.put("email", request.getEmail());
            nullResponse.put("phoneNumber", request.getPhoneNumber());
            nullResponse.put("nickname", request.getNickname());
            nullResponse.put("password", request.getPassword());
            nullResponse.put("passwordConfirm", request.getPasswordConfirm());
            throw new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("필수 입력값 누락")
                    .request(nullResponse)
                    .build();
        } else if(request.badEmailValue()){
            throw new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("잘못된 이메일")
                    .request(email)
                    .build();
        } else if (request.badPhoneNumValue()) {
            throw new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("잘못된 핸드폰 번호")
                    .request(phoneNumber)
                    .build();
        } else if (request.badNicknameValue()){
            throw new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("핸드폰 번호 형식으로 작성된 닉네임")
                    .request(nickname)
                    .build();
        } else if(request.badPasswordValue()){
            throw new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("최소 조건을 충족하지 못한 비밀번호")
                    .request(password)
                    .build();
        } else if (request.differentPasswordConfirm()) {
            Map<String, String> passwordConfirmError = new LinkedHashMap<>();
            passwordConfirmError.put("password", password);
            passwordConfirmError.put("passwordConfirm", request.getPasswordConfirm());
            throw new CustomBadRequestException.ExceptionBuilder()
                    .customMessage("서로 다른 비밀번호와 비밀번호 확인")
                    .request(passwordConfirmError)
                    .build();
        }

        //성별 별 기본 프사 설정
        if(request.getProfileImg() == null){
            if(request.getGender()==null||request.getGender()==Gender.UNKNOWN)request.setProfileImg ("https://uxwing.com/wp-content/themes/uxwing/download/communication-chat-call/id-card-line-icon.png");
            else if(request.getGender()==Gender.MALE) request.setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/elderly-man-icon.png");
            else request.setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/young-girl-icon.png");
        }

    }


    @Transactional
    public MyUser failureCounting(String principal) {
        MyUser failUser = findMyUser(principal);
        failUser.loginValueSetting(true);
        return failUser;
    }

    @Transactional
    public void loginSuccessEvent(String principal) {
        MyUser sucUser = findMyUser(principal);
        sucUser.loginValueSetting(false);
    }
}
