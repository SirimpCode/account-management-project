package com.github.accountmanagementproject.repository.account.socialids;

import java.util.Optional;

public interface SocialIdsQueryRepository {

    Optional<SocialId> findBySocialIdPkJoinMyUser(SocialIdPk socialIdPk);
}
