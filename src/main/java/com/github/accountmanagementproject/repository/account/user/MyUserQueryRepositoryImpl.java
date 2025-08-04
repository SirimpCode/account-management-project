package com.github.accountmanagementproject.repository.account.user;

import com.github.accountmanagementproject.common.exceptions.CustomBadRequestException;
import com.github.accountmanagementproject.repository.account.role.Role;
import com.github.accountmanagementproject.repository.account.socialid.QSocialId;
import com.github.accountmanagementproject.repository.account.socialid.SocialIdPk;
import com.github.accountmanagementproject.repository.account.role.QRole;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MyUserQueryRepositoryImpl implements MyUserQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final QMyUser qMyUser = QMyUser.myUser;

    @Override
    public Optional<MyUser> findBySocialIdPkOrUserEmail(SocialIdPk socialIdPk, String email) {
        QSocialId qSocialId = QSocialId.socialId;
        List<MyUser> myUserList = queryFactory.select(qMyUser)
                .from(qMyUser)
                .leftJoin(qMyUser.socialIds, qSocialId).fetchJoin()
                .where(qSocialId.socialIdPk.eq(socialIdPk).or(qMyUser.email.eq(email)))
                .fetch();
        MyUser response = singleOutAUser(myUserList, socialIdPk);
        return Optional.ofNullable(response);
    }

    private BooleanExpression emailOrPhoneNumberPredicate(String emailOrPhoneNumber) {
        if (emailOrPhoneNumber.matches("01\\d{9}")) {
            return qMyUser.phoneNumber.eq(emailOrPhoneNumber);
        } else if (emailOrPhoneNumber.matches(".+@.+\\..+")) {
            return qMyUser.email.eq(emailOrPhoneNumber);
        }
        throw CustomBadRequestException.of()
                .customMessage("잘못 입력된 식별자")
                .request(emailOrPhoneNumber)
                .build();
    }
    @Override
    public Optional<MyUser> findByEmailOrPhoneNumber(String emailOrPhoneNumber) {
        BooleanExpression emailOrPhoneNumberPredicate = emailOrPhoneNumberPredicate(emailOrPhoneNumber);

        List<MyUser> user = queryFactory
                .from(qMyUser)
                .join(qMyUser.roles, QRole.role)
                .where(emailOrPhoneNumberPredicate)
                .transform(
                        GroupBy.groupBy(qMyUser.email).list(
                                Projections.fields(MyUser.class,
                                        qMyUser.email,
                                        qMyUser.nickname,
                                        qMyUser.password,
                                        qMyUser.failureCount,
                                        qMyUser.status,
                                        qMyUser.failureDate,
                                        qMyUser.createdAt,
                                        qMyUser.lastLogin,
                                        GroupBy.set(
                                                Projections.fields(Role.class,
                                                        QRole.role.name)).as("roles")))
                );


        return Optional.ofNullable(user.size()==1?user.get(0):null);
    }

    @Override
    public void updateFailureCountByEmail(MyUser failUser) {
        queryFactory.update(qMyUser)
                .set(qMyUser.failureCount, failUser.getFailureCount())
                .set(qMyUser.failureDate, failUser.getFailureDate())
                .set(qMyUser.status, failUser.getStatus())
                .set(qMyUser.lastLogin, failUser.getLastLogin())
                .where(qMyUser.email.eq(failUser.getEmail()))
                .execute();
    }

    /**
     * 만약 소셜아이디로 가입이 되어있지만 해당 계정의 소셜이메일로 다른계정이 가입되어있을경우
     * 여러 계정중 소셜아이디의 계정으로 로그인 시도. 또는 소셜아이디는 찾을 수 없지만 소셜이메일과 같은 계정이 존재할 경우엔
     * 그냥 해당계정으로 로그인시도 (상위 메서드에서 소셜아이디를 추가 해줌)
     */
    private MyUser singleOutAUser(List<MyUser> myUserList, SocialIdPk socialIdPk) {
        return myUserList.isEmpty() ? null :
                myUserList.size() == 1 ? myUserList.get(0) :
                        myUserList.stream().filter(user ->
                                        user.getSocialIds().stream().anyMatch(id -> id.getSocialIdPk().equals(socialIdPk)))
                                .findFirst().orElse(null);
    }
}
