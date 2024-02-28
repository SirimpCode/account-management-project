package com.github.accountmanagementproject.web.dto.account;

import com.fasterxml.jackson.annotation.*;
import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirm;
    private String nickname;
    private String phoneNumber;
    private Gender gender;
    private String dateOfBirth;
    private String profileImg;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastLogin;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RolesEnum> roles;


    public boolean requiredNull(){
        return this.email==null
                ||this.password==null
                ||this.passwordConfirm==null
                ||this.nickname==null
                ||this.phoneNumber==null;
    }
    public boolean badEmailValue(){
        return !this.email.matches(".+@.+\\..+");
    }
    public boolean badPhoneNumValue(){
        return !this.phoneNumber.matches("01\\d{9}");
    }
    public boolean badNicknameValue(){
        return this.getNickname().matches("01\\d{9}");
    }
    public boolean badPasswordValue(){
        return !this.password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$")
                ||!(this.password.length()>=8&&password.length()<=20);
    }
    public boolean differentPasswordConfirm(){
        return !this.passwordConfirm.equals(this.password);
    }

}
