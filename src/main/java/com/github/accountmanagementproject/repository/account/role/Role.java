package com.github.accountmanagementproject.repository.account.role;

import com.github.accountmanagementproject.common.converter.custom.RoleConverter;
import com.github.accountmanagementproject.common.myenum.RolesEnum;
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
    private RolesEnum name;


    public Role(Integer id) {
        this.rolesId = id;
    }
    public Role(RolesEnum name) {
        this.name = name;
    }
}

