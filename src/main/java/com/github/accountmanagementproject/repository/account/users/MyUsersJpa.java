package com.github.accountmanagementproject.repository.account.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;


@Repository
public interface MyUsersJpa extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByEmail(String email);
    Optional<MyUser> findByPhoneNumber(String phoneNumber);


    @Query(
            "SELECT u " +
                    "FROM MyUser u " +
                    "JOIN FETCH u.roles r " +
                    "WHERE u.email = :email"
    )
    Optional<MyUser> findByEmailJoin(String email);
    @Query(
            "SELECT u " +
                    "FROM MyUser u " +
                    "JOIN FETCH u.roles r " +
                    "WHERE u.phoneNumber = ?1"
    )
    Optional<MyUser> findByPhoneNumberJoin(String phoneNumber);

    @Modifying
    @Query(
            "UPDATE MyUser u " +
                    "SET  u.failureCount = u.failureCount + 1 " +
                    "WHERE u.email = :email OR u.phoneNumber = :email"
    )
    int updateFailureCountByEmail(String email);

    MyUser findBySocialId(BigInteger socialId);
//
//    @Query(
//            "SELECT u " +
//                    "FROM MyUser u " +
//                    "JOIN FETCH u.userRoles ur " +
//                    "JOIN FETCH ur.roles " +
//                    "WHERE u.userId = ?1"
//    )
//    Optional<MyUser> findByIdJoin(Integer userId);





}
