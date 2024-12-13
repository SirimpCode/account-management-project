package com.github.accountmanagementproject.repository.account.users;

import com.github.accountmanagementproject.repository.account.socialids.SocialIdPk;

import java.util.Optional;

public interface MyUsersQueryRepository {

    Optional<MyUser> findBySocialIdPkOrUserEmail(SocialIdPk socialIdPk, String email);
}
