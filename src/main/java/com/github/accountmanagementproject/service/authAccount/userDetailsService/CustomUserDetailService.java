package com.github.accountmanagementproject.service.authAccount.userDetailsService;

import com.github.accountmanagementproject.repository.account.users.CustomUserDetails;
import com.github.accountmanagementproject.repository.account.users.User;
import com.github.accountmanagementproject.repository.account.users.UsersJpa;
import com.github.accountmanagementproject.service.customExceptions.CustomBadRequestException;
import com.github.accountmanagementproject.service.customExceptions.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UsersJpa usersJpa;

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) {
        User user;
        if(emailOrPhoneNumber.matches("01\\d{9}")){
            user = usersJpa.findByPhoneNumberJoin(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException("입력하신 핸드폰 번호의 계정이 없습니다.",emailOrPhoneNumber));
        }else if (emailOrPhoneNumber.matches(".+@.+\\..+")){
            user = usersJpa.findByEmailJoin(emailOrPhoneNumber).orElseThrow(()->
                    new CustomNotFoundException("입력하신 이메일의 계정이 없습니다.",emailOrPhoneNumber));
        }else throw new CustomBadRequestException("입력하신 이메일 또는 핸드폰 번호를 확인해주세요.",emailOrPhoneNumber);


        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        return CustomUserDetails.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .authorities(user.getRoles().stream()
                        .map(roles->new SimpleGrantedAuthority(roles.getName().name()))
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
