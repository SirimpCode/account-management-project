package com.github.accountmanagementproject.service.authAccount.userDetailsService;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.service.customExceptions.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MyUsersJpa myUsersJpa;

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) {
        MyUser myUser;
        if(emailOrPhoneNumber.matches("01\\d{9}")){
            myUser = myUsersJpa.findByPhoneNumberJoin(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException("입력하신 핸드폰 번호의 계정이 없습니다.",emailOrPhoneNumber));
        }else if (emailOrPhoneNumber.matches(".+@.+\\..+")){
            myUser = myUsersJpa.findByEmailJoin(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException("입력하신 이메일의 계정이 없습니다.",emailOrPhoneNumber));
        }else throw new CustomBadRequestException("입력하신 이메일 또는 핸드폰 번호를 확인해주세요.",emailOrPhoneNumber);


        return createUserDetails(myUser);
    }

    private UserDetails createUserDetails(MyUser myUser) {
        return User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .authorities(myUser.getRoles().stream()
                        .map(roles->new SimpleGrantedAuthority(roles.getName().name()))
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
