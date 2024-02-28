package com.github.accountmanagementproject.config.security;

import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.repository.account.users.roles.RolesJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccountConfig {
    private final RolesJpa rolesJpa;

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
            throw new CustomBadRequestException("필수 입력값 누락", nullResponse);
        }


        if(request.badEmailValue()){
            throw new CustomBadRequestException("잘못된 이메일",email);
        } else if (request.badPhoneNumValue()) {
            throw new CustomBadRequestException("잘못된 핸드폰 번호", phoneNumber);
        } else if (request.badNicknameValue()){
            throw new CustomBadRequestException("핸드폰 번호 형식으로 작성된 닉네임",nickname);
        }
        else if(request.badPasswordValue()){
            throw new CustomBadRequestException("최소 조건을 충족하지 못한 비밀번호",password);
        } else if (request.differentPasswordConfirm()) {
            Map<String, String> passwordConfirmError = new LinkedHashMap<>();
            passwordConfirmError.put("password", password);
            passwordConfirmError.put("passwordConfirm", request.getPasswordConfirm());
            throw new CustomBadRequestException("서로 다른 비밀번호와 비밀번호 확인",passwordConfirmError);
        }


        //성별 별 기본 프사 설정

        if(request.getProfileImg() == null){
            if(request.getGender()==null||request.getGender()==Gender.UNKNOWN)request.setProfileImg ("https://uxwing.com/wp-content/themes/uxwing/download/communication-chat-call/id-card-line-icon.png");
            else if(request.getGender()==Gender.MALE) request.setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/elderly-man-icon.png");
            else request.setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/young-girl-icon.png");
        }


    }
}
