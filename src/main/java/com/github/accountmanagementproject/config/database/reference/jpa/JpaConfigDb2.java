//package com.github.accountmanagementproject.config.database.jpa;
//
//import jakarta.persistence.EntityManagerFactory;
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
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "com.github.jpaquerydslmybatis.repository.db2.jpa",
//        entityManagerFactoryRef = "db2EntityManagerFactory",
//        transactionManagerRef = "db2TransactionManager"
//)
//public class JpaConfigDb2 {
//
//    @Bean//data source yml에서 설정된 db2 datasource를 사용
//    @ConfigurationProperties("spring.datasource.db2")
//    public DataSourceProperties db2DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    @Bean
//    public DataSource db2DataSource() {
//        return db2DataSourceProperties()
//                .initializeDataSourceBuilder()
//                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
//            @Qualifier("db2DataSource") DataSource dataSource,
//            JpaProperties jpaProperties) {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setShowSql(jpaProperties.isShowSql());
//
//        //vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setJpaVendorAdapter(vendorAdapter);
//
//        factory.setPersistenceUnitName("db2PU");
//        // repository 패키지 설정 위의 basePackages 가 설정되어 있지만
//        // 위의 것은 JpaRepository 를 위한 설정이고 아래의 설정은 엔티티 클래스를 위한설정
//        factory.setPackagesToScan("com.github.jpaquerydslmybatis.repository.db2.jpa");
//
//        //네이밍전략 적용
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
//        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//        factory.setJpaPropertyMap(properties);
//
//
//        return factory;
//    }
//    @Bean
//    public PlatformTransactionManager db2TransactionManager(
//            @Qualifier("db2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory);
//        return txManager;
//    }
//
//}
