package com.github.accountmanagementproject.repository.account.users.roles;

import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role findByName(RolesEnum name);
}
