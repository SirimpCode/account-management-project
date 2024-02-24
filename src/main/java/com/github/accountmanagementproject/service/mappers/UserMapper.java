package com.github.accountmanagementproject.service.mappers;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.service.mappers.converter.GenderConverter;
import com.github.accountmanagementproject.web.dto.account.AccountDto;
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
    @Mapping(target = "gender", source = "myUser.gender.value")
    @Mapping(target = "dateOfBirth", dateFormat = "yyyy년 M월 d일")
//    @Mapping(target = "lastLogin", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AccountDto myUserToAccountDto(MyUser myUser);

    @Mapping(target = "dateOfBirth", dateFormat = "yyyy년 M월 d일")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "gender", qualifiedByName = "strToEnum")
    MyUser accountDtoToMyUser(AccountDto accountDto);

    @Named("strToEnum")
    default Gender strToEnum(String str){
        return new GenderConverter().convertToEntityAttribute(str);
    }
    @Named("getMyRoles")
    default Set<String> myRoles(Set<Role> roles){
        return roles.stream().map(r->r.getName().getKor()).collect(Collectors.toSet());
    }

}
