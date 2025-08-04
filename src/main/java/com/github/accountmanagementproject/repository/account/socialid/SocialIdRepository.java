package com.github.accountmanagementproject.repository.account.socialid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialIdRepository extends JpaRepository<SocialId, SocialIdPk>, SocialIdQueryRepository {
}
