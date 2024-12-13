package com.github.accountmanagementproject.repository.account.users.roles;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.service.mappers.converter.RoleConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    @ManyToMany(mappedBy = "roles")
    private Set<MyUser> myUsers;

    public Role(Integer id) {
        this.rolesId = id;
    }
}

