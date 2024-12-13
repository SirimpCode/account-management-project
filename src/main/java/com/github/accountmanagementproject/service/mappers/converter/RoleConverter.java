package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import org.springframework.stereotype.Component;

public class RoleConverter extends MyConverter<RolesEnum> {
    public RoleConverter() {
        super(RolesEnum.class);
    }
}
