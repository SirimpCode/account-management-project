package com.github.accountmanagementproject.repository.account.users;

import com.github.accountmanagementproject.repository.account.socialIds.SocialIdPk;

import java.util.Optional;

public interface MyUsersDaoCustom {

    Optional<MyUser> findBySocialIdPk(SocialIdPk socialIdPk);
}
