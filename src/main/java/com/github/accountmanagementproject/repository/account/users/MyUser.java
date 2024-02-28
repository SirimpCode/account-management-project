package com.github.accountmanagementproject.repository.account.users;

import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;
import com.github.accountmanagementproject.service.mappers.converter.GenderConverter;
import com.github.accountmanagementproject.service.mappers.converter.UserStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@DynamicInsert
@Entity
@Table(name = "users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "social_id", unique = true)
    private Integer socialId;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    private String nickname;

    @Column(name = "phone_number", unique = true, nullable = false, length = 15)
    private String phoneNumber;


    @Column(nullable = false)
    private String password;

    @Convert(converter = GenderConverter.class)
    @Column(length = 4)
    private Gender gender;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Convert(converter = UserStatusConverter.class)
    @Column(length = 10)
    private UserStatus status;

    @Column(name = "failure_count", columnDefinition = "INT DEFAULT 0")
    private Integer failureCount;

    @Column(name = "withdrawal_date")
    private LocalDateTime withdrawalDate;

    @Column(name = "lock_date")
    private LocalDateTime lockDate;

    @ManyToMany(fetch = FetchType.LAZY)//⬅️기본값이 lazy 이기 때문에 굳이 명시적으로 작성할 필요는 없음
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),//참조할 fk
            inverseJoinColumns = @JoinColumn(name = "role_id"))//상대 엔티티에서 참조할 fk
    private Set<Role> roles = new HashSet<>();//널포인트익셉션 방지하기위해 빈 HashSet 적용



//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.userRoles.stream().map(userRole -> userRole.getRoles())
//                .map(role -> role.getName())
//                .map(roles->new SimpleGrantedAuthority(roles))
//                .toList();
//    }
}
