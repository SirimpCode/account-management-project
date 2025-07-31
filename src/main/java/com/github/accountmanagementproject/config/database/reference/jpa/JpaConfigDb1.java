//package com.github.accountmanagementproject.config.database.jpa;
//
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
///*
//* 여기서 주의해야 할 것은 Db1ConfigSecond 에 @Primary 어노테이션이 붙었다는 것이다.
//이는 spring data 에서 default properties 는 단일 데이터소스를 기반으로 하기 때문에 그렇다
//* */
////추후 여러개의 데이터소스를 사용할 때를 위한 참고 클래스
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "com.github.jpaquerydslmybatis.repository.db1.jpa",// JpaRepository 를 스캔할 패키지 즉 JpaRepository 클래스가 있는 패키지
//        entityManagerFactoryRef = "db1EntityManagerFactory", // JpaRepository 를 위한 EntityManagerFactory 빈 이름
//        transactionManagerRef = "db1TransactionManager" // JpaRepository 를 위한 TransactionManager 빈 이름
//)
//public class JpaConfigDb1 {
//
//
//
//    @Bean//yml 에 설정된 spring.datasource.db1 정보로 DataSourceProperties 객체를 생성
//    @ConfigurationProperties("spring.datasource.db1")
//    public DataSourceProperties db1DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    @Bean
//    public DataSource db1DataSource() {
//        return db1DataSourceProperties()
//                .initializeDataSourceBuilder()
//                .build();
//    }//여기까지가 dataSource 설정
//
//    @Bean
//    //@Primary // @Primary 어노테이션은 이 빈이 기본적으로 사용될 빈임을 나타낸다.
//    // 없을시 반드시 @Qualifier("db1EntityManagerFactory") 어노테이션을 사용해야 한다.
//    //단일 일땐 EntityManager 를 spring이 자동으로 생성해주지만
//    //여러개를 사용할 때는 LocalContainerEntityManagerFactoryBean 를 사용해 여러 커스텀설정을 적용해준다.
//    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
//            JpaProperties jpaProperties,
//            @Qualifier("db1DataSource") DataSource dataSource) {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        //show sql 설정
//        vendorAdapter.setShowSql(jpaProperties.isShowSql());
////        jpaProperties.getOpenInView();
//        // 엔티티 기반 ddl 자동 생성 여부 설정
////        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setJpaVendorAdapter(vendorAdapter);
//
//        factory.setPersistenceUnitName("db1PU");
//        // repository 패키지 설정 위의 basePackages 가 설정되어 있지만
//        // 위의 것은 JpaRepository 를 위한 설정이고 아래의 설정은 엔티티 클래스를 위한설정
//        factory.setPackagesToScan("com.github.jpaquerydslmybatis.repository.db1.jpa");
//
//        //네이밍전략 적용 db의  snake_case 를 자바의 camelCase로 변환해주는 전략
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
//        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//        factory.setJpaPropertyMap(properties);
//
//
//        return factory;
//    }
//    @Bean//이게 mybatis 에도 적용이된다. 같은 데이터소스를 사용할것이기때문
//    public PlatformTransactionManager db1TransactionManager(
//            @Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        // 세터 없이 그냥 생성자로 반환해주면됨.
////        JpaTransactionManager txManager = new JpaTransactionManager();
////        txManager.setEntityManagerFactory(entityManagerFactory);
//        return new JpaTransactionManager(entityManagerFactory);
//    } //@Transactional(value = "db1TransactionManager", readOnly = true)
//
//}
