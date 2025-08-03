package com.github.accountmanagementproject.service.account.auth.userdetails;

import com.github.accountmanagementproject.repository.account.user.MyUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    private final MyUser myUser;

    private CustomUserDetails(MyUser myUser){
        this.myUser = myUser;
    }
    public static CustomUserDetails of(MyUser myUser){
        return new CustomUserDetails(myUser);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return myUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        return myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return myUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !myUser.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !myUser.isLocked() || myUser.isUnlockTime();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !myUser.isDisabled();
    }
}
