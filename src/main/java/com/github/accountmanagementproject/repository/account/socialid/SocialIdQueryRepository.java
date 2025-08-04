package com.github.accountmanagementproject.repository.account.socialid;

import java.util.Optional;

public interface SocialIdQueryRepository {

    Optional<SocialId> findBySocialIdPkJoinMyUser(SocialIdPk socialIdPk);
}
