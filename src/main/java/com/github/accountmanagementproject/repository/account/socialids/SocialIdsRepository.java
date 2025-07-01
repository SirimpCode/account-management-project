package com.github.accountmanagementproject.repository.account.socialids;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialIdsRepository extends JpaRepository<SocialId, SocialIdPk>, SocialIdsQueryRepository {
}
