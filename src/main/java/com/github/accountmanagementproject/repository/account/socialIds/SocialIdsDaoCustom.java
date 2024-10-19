package com.github.accountmanagementproject.repository.account.socialIds;

import java.util.Optional;

public interface SocialIdsDaoCustom {

    Optional<SocialId> findBySocialIdPkJoinMyUser(SocialIdPk socialIdPk);
}
