package com.github.accountmanagementproject.common.security.userdetails;

import com.github.accountmanagementproject.common.myenum.RoleEnum;
import com.github.accountmanagementproject.common.myenum.UserStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {
    private long userId;
    private String email;
    private String nickname;
    private String password;
    private UserStatus status;
    private int failureCount;
    private LocalDateTime failureDate;
    private LocalDateTime withdrawalDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Set<RoleEnum> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked() || this.isUnlockTime();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDisabled();
    }

    private boolean isExpired() {
        if (lastLogin == null) return this.createdAt.isBefore(LocalDateTime.now().minusMonths(3));
        return this.lastLogin.isBefore(LocalDateTime.now().minusMonths(3));
    }
    private boolean isLocked() {
        return this.status == UserStatus.LOCK;
    }

    public boolean isTempAccount() {
        return this.status == UserStatus.TEMP;
    }
    public boolean isDisabled() {
        return this.status == UserStatus.WITHDRAWAL || this.status == UserStatus.TEMP;
    }

    private boolean isUnlockTime() {
        return this.failureDate != null
                && this.failureDate.isBefore(LocalDateTime.now().minusMinutes(5));
    }
    //로그인 실패 또는 성공시의 변화 될 값 DB에 반영하기 위해
    public void loginValueSetting(boolean failure) {
        //5번째 시도이고 5분이내 한번 더 시도했을시 잠금처리
        this.status = failure ?
                (isFailureCountingOrLocking() || isUnlockTime() ? UserStatus.NORMAL : UserStatus.LOCK)
                : UserStatus.NORMAL;
        //실패시 failureCount 를 1 증가시킨다. 단 계정이 잠길땐 0으로 만들고, 실패한지 5분 이상 지났을시 1부터 다시시작
        this.failureCount = failure ?
                (isUnlockTime() ?
                        1
                        : (isFailureCountingOrLocking() ? failureCount + 1 : 0))
                : 0;
        this.failureDate = failure ? LocalDateTime.now() : null;
        this.lastLogin = !failure ? LocalDateTime.now() : this.lastLogin;
    }
    public boolean isFailureCountingOrLocking() {
        return this.failureCount < 4;
    }


}
