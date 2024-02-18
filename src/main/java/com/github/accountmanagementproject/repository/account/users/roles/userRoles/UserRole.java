//package com.github.accountmanagementproject.repository.account.userRoles;
//
//import com.github.accountmanagementproject.repository.account.users.User;
//import com.github.accountmanagementproject.repository.account.users.roles.Role;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "user_roles")
//@Entity
//public class UserRole {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_roles_id")
//    private Integer userRolesId;
//    @ManyToOne
//    @JoinColumn(name = "user_id",nullable = false)
//    private User userEntity;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id",nullable = false)
//    private Role roles;
//}
