package com.github.accountmanagementproject.service.mappers;

import com.github.accountmanagementproject.config.client.oauth.dto.userinfo.OAuthUserInfo;
import com.github.accountmanagementproject.repository.account.socialids.SocialId;
import com.github.accountmanagementproject.repository.account.socialids.SocialIdPk;
import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import com.github.accountmanagementproject.repository.account.users.roles.Role;
import com.github.accountmanagementproject.web.dto.account.auth.request.SignUpRequest;
import com.github.accountmanagementproject.web.dto.account.auth.response.MyInfoResponse;
import com.github.accountmanagementproject.web.dto.account.oauth.response.OAuthSignUpDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", qualifiedByName = "getMyRoles")
    @Mapping(target = "gender", source = "myUser.gender")
    @Mapping(target = "dateOfBirth", dateFormat = "yyyy년 M월 d일")
    @Mapping(target = "lastLogin", dateFormat = "yyyy년 M월 d일 HH:mm:ss")
    MyInfoResponse myUserToAccountDto(MyUser myUser);

    @Mapping(target = "dateOfBirth", dateFormat = "yyyy-M-d")
    @Mapping(target = "roles", ignore = true)
    MyUser accountDtoToMyUser(SignUpRequest signUpRequest);

    @Named("getMyRoles")
    default Set<RolesEnum> myRoles(Set<Role> roles){
        return roles.stream().map(r->r.getName())
                .collect(Collectors.toSet());
    }

////닉네임이 고유값이어야 할때    @Mapping(target = "nickname", expression = "java(oAuthInfoResponse.getNickName()+\"_\"+oAuthInfoResponse.getSocialId())")
    @Mapping(target = "password", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "TEMP")
    MyUser oAuthInfoResponseToMyUser(OAuthUserInfo oAuthUserInfo);

    @AfterMapping
    default void assignSocialId(OAuthUserInfo oAuthUserInfo, @MappingTarget MyUser myUser){
        SocialIdPk socialIdPk = SocialIdPk.of(oAuthUserInfo.getSocialId(), oAuthUserInfo.getOAuthProvider());
        SocialId newSocialId = SocialId.ofSocialIdPkAndMyUser(socialIdPk, myUser);
        myUser.addSocialId(newSocialId);
    }
    @Mapping(target = "provider", source = "OAuthProvider")
    OAuthSignUpDto oAuthUserInfoToOAuthSignUpDto(OAuthUserInfo oAuthUserInfo);
}
