package com.github.accountmanagementproject.repository.account.user;

import com.github.accountmanagementproject.repository.account.socialid.SocialIdPk;
import com.github.accountmanagementproject.common.security.userdetails.CustomUserDetails;

import java.util.Optional;

public interface MyUserQueryRepository {

    Optional<MyUser> findBySocialIdPkOrUserEmail(SocialIdPk socialIdPk, String email);

    Optional<CustomUserDetails> findByEmailOrPhoneNumberForAuth(String emailOrPhoneNumber);

    void updateFailureCountByEmail(CustomUserDetails failUser);
}
