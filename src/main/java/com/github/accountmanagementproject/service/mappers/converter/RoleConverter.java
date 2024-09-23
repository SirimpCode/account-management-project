package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;

public class RoleConverter extends MyConverter<RolesEnum> {
    public static final Class<RolesEnum> ENUM_CLASS = RolesEnum.class;

    public RoleConverter() {
        super(ENUM_CLASS);
    }
}
