//package com.github.accountmanagementproject.config.database.jpa;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QuerydslConfigDb1 {
//    // JpaConfig 에서 factory.setPersistenceUnitName("db1PU"); 로 유닛이름을 지정해준것을 여기서 사용
//    @PersistenceContext(unitName = "db1PU")
//    private EntityManager em;
//
//    @Bean
//    public JPAQueryFactory db1QueryFactory() {
//        return new JPAQueryFactory(em);
//    }
//    /*
//    private final JPAQueryFactory db1QueryFactory;
//
//    public MemberRepositoryCustomImpl(@Qualifier("db1QueryFactory") JPAQueryFactory db1QueryFactory) {
//        this.db1QueryFactory = db1QueryFactory;
//    }
//    */
//}
