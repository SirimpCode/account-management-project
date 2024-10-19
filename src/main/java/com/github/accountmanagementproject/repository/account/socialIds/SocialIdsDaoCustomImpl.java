package com.github.accountmanagementproject.repository.account.socialIds;

import com.github.accountmanagementproject.repository.account.users.QMyUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class SocialIdsDaoCustomImpl implements SocialIdsDaoCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<SocialId> findBySocialIdPkJoinMyUser(SocialIdPk socialIdPk) {
        QSocialId qSocialId = QSocialId.socialId;

        SocialId socialId = queryFactory
                .selectFrom(qSocialId)
                .join(qSocialId.myUser, QMyUser.myUser).fetchJoin()
                .where(qSocialId.socialIdPk.eq(socialIdPk))
                .fetchOne();

        return socialId == null ? Optional.empty() : Optional.of(socialId);
    }
}
