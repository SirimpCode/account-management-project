package com.github.accountmanagementproject.repository.account.users;

import com.github.accountmanagementproject.repository.account.socialids.SocialIdPk;

import java.util.Optional;

public interface MyUsersDaoCustom {

    Optional<MyUser> findBySocialIdPkNormalOrTemp(SocialIdPk socialIdPk);
}
