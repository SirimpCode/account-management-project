package com.github.accountmanagementproject.repository.account.role;

import com.github.accountmanagementproject.common.converter.custom.RoleConverter;
import com.github.accountmanagementproject.common.myenum.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer rolesId;


    @Convert(converter = RoleConverter.class)
    @Column(length = 4)
    private RoleEnum name;


    private Role(Integer id) {
        this.rolesId = id;
    }
    public static Role fromName(RoleEnum name) {
        return switch (name) {
            case ROLE_ADMIN -> new Role(1);
            case ROLE_USER -> new Role(2);
            case ROLE_SUPER_USER -> new Role(3);
        };
    }
}

