package com.github.accountmanagementproject.repository.account.socialids;

import java.util.Optional;

public interface SocialIdsDaoCustom {

    Optional<SocialId> findBySocialIdPkJoinMyUser(SocialIdPk socialIdPk);
}
