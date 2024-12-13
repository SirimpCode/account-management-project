package com.github.accountmanagementproject.repository.account.users;

import com.github.accountmanagementproject.repository.account.socialids.QSocialId;
import com.github.accountmanagementproject.repository.account.socialids.SocialIdPk;
import com.github.accountmanagementproject.repository.account.users.enums.UserStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MyUsersDaoCustomImpl implements MyUsersDaoCustom {
    private final JPAQueryFactory queryFactory;
    private final QMyUser qMyUser = QMyUser.myUser;

    @Override
    public Optional<MyUser> findBySocialIdPkNormalOrTemp(SocialIdPk socialIdPk) {
        BooleanExpression normal = qMyUser.status.in(UserStatus.NORMAL, UserStatus.TEMP);
        QSocialId qSocialId = QSocialId.socialId;
        MyUser myUser = queryFactory.select(qSocialId.myUser)
                .from(qSocialId)
                .where(qSocialId.socialIdPk.eq(socialIdPk))
                .fetchOne();

        return Optional.ofNullable(myUser);
    }
}
