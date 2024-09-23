package com.github.accountmanagementproject.service.mappers;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.web.dto.accountAuth.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", qualifiedByName = "getMyRoles")
    @Mapping(target = "gender", source = "myUser.gender")
    @Mapping(target = "dateOfBirth", dateFormat = "yyyy년 M월 d일")
//    @Mapping(target = "lastLogin", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AccountDto myUserToAccountDto(MyUser myUser);

    @Mapping(target = "dateOfBirth", dateFormat = "yyyy-M-d")
    @Mapping(target = "roles", ignore = true)
    MyUser accountDtoToMyUser(AccountDto accountDto);

    @Named("getMyRoles")
    default Set<RolesEnum> myRoles(Set<Role> roles){
        return roles.stream().map(r->r.getName())
                .collect(Collectors.toSet());
    }

}
