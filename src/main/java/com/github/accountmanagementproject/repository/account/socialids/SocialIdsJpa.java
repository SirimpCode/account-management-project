package com.github.accountmanagementproject.repository.account.socialids;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialIdsJpa extends JpaRepository<SocialId, Integer>, SocialIdsDaoCustom {
}
