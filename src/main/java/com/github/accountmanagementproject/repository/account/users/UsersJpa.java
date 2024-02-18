package com.github.accountmanagementproject.repository.account.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersJpa extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);


    @Query(
            "SELECT u " +
                    "FROM User u " +
                    "JOIN FETCH u.roles r " +
                    "WHERE u.email = :email"
    )
    Optional<User> findByEmailJoin(String email);
    @Query(
            "SELECT u " +
                    "FROM User u " +
                    "JOIN FETCH u.roles r " +
                    "WHERE u.phoneNumber = ?1"
    )
    Optional<User> findByPhoneNumberJoin(String phoneNumber);
//
//    @Query(
//            "SELECT u " +
//                    "FROM User u " +
//                    "JOIN FETCH u.userRoles ur " +
//                    "JOIN FETCH ur.roles " +
//                    "WHERE u.userId = ?1"
//    )
//    Optional<User> findByIdJoin(Integer userId);





}
