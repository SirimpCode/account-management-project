package com.github.accountmanagementproject.config.security;

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

        if(email==null||nickname==null||phoneNumber==null||password==null||request.getPasswordConfirm()==null){
            throw new CustomBadRequestException("회원가입에 필수 입력값이 누락 되었습니다.", request);
        }


        if(!email.matches(".+@.+\\..+")){
            throw new CustomBadRequestException("이메일을 정확히 입력해주세요.",email);
        } else if (!phoneNumber.matches("01\\d{9}")) {
            throw new CustomBadRequestException("핸드폰 번호를 확인해주세요.", phoneNumber);
        } else if (request.getNickname().matches("01\\d{9}")){
            throw new CustomBadRequestException("핸드폰 번호를 닉네임으로 사용할수 없습니다.",nickname);
        }

//        if(usersJpa.existsByEmailOrPhoneNumberOrNickname(email, phoneNumber, nickname)){
//            Map<String, String> duplicateResponse = new LinkedHashMap<>();
//            duplicateResponse.put("email", email);
//            duplicateResponse.put("phoneNumber", phoneNumber);
//            duplicateResponse.put("nickname", nickname);
//            throw new DuplicateKeyException("이메일, 핸드폰 번호, 닉네임 세값중 중복 값이 있습니다. 중복 확인을 해주세요.", duplicateResponse);
//        }
        else if(!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$")
                ||!(password.length()>=8&&password.length()<=20)
        ){
            throw new CustomBadRequestException("비밀번호는 8자 이상 20자 이하 숫자와 영문자 조합 이어야 합니다.",password);
        } else if (!request.getPasswordConfirm().equals(password)) {
            Map<String, String> passwordConfirmError = new LinkedHashMap<>();
            passwordConfirmError.put("password", password);
            passwordConfirmError.put("passwordConfirm", request.getPasswordConfirm());
            throw new CustomBadRequestException("비밀번호와 비밀번호 확인이 같지 않습니다.",passwordConfirmError);
        }


        //성별 별 기본 프사 설정
        if(request.getGender()==null||request.getGender().equals("남성")||request.getGender().equals("여성")||request.getGender().equals("미정")){
            if(request.getProfileImg() == null){
                if(request.getGender()==null||request.getGender().equals("미정"))request.setProfileImg ("https://uxwing.com/wp-content/themes/uxwing/download/communication-chat-call/id-card-line-icon.png");
                else if(request.getGender().equals("남성")) request.setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/elderly-man-icon.png");
                else request.setProfileImg("https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/young-girl-icon.png");
            }
        } else throw new CustomBadRequestException("성별은 남성, 여성 또는 미정 이어야 합니다.", request.getGender());


    }
}
