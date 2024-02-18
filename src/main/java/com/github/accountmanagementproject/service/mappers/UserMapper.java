package com.github.accountmanagementproject.service.mappers;

import com.github.accountmanagementproject.repository.account.users.User;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "roles", ignore = true)
    User AccountDtoToUserEntity(AccountDto accountDto);



}
