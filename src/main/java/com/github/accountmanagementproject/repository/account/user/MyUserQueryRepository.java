package com.github.accountmanagementproject.repository.account.user;

import com.github.accountmanagementproject.repository.account.socialid.SocialIdPk;

import java.util.Optional;

public interface MyUserQueryRepository {

    Optional<MyUser> findBySocialIdPkOrUserEmail(SocialIdPk socialIdPk, String email);

    Optional<MyUser> findByEmailOrPhoneNumber(String emailOrPhoneNumber);

    void updateFailureCountByEmail(MyUser failUser);
}
