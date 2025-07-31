//package com.github.accountmanagementproject.config.database.jpa;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QuerydslConfigDb2 {
//    @PersistenceContext(unitName = "db2PU")
//    private EntityManager em;
//
//    @Bean
//    public JPAQueryFactory db2QueryFactory() {
//        return new JPAQueryFactory(em);
//    }
//}
