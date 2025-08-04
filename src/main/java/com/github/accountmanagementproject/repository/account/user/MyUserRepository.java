package com.github.accountmanagementproject.repository.account.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer>, MyUserQueryRepository {
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





    boolean existsByEmail(String email);

}
