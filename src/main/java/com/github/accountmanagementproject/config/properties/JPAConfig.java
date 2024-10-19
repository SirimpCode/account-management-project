package com.github.accountmanagementproject.config.properties;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//아래 어노태이션으로 엔티티의 생성시간 수정시간 등을 쉽게 적용할수있다.
//실행클래스에 적용해놨다가 이쪽으로 옮겨옴
@EnableJpaAuditing
public class JPAConfig {
    //PersistenceContext 어노테이션으로 엔티티매니저를 주입받는다.
    @PersistenceContext
    private EntityManager em;
    //jpaQueryFactory queryDSL에서 사용되는 쿼리팩토리로 빈으로 구성시켜 놓는다.
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}

