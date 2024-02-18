package com.github.accountmanagementproject.repository.account.users.roles;

import com.github.accountmanagementproject.repository.account.users.User;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roles_id")
    private Integer rolesId;

    @Enumerated(EnumType.STRING)
    @Column(length = 4)
    private RolesEnum name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

