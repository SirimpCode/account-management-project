//package com.github.accountmanagementproject.config.database.mybatis;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(
//        basePackages = "com.github.jpaquerydslmybatis.repository.db2.mybatis",
//        sqlSessionFactoryRef = "db2SqlSessionFactory"
//)
//public class MybatisConfigDb2 {
//
//    @Value("${mybatis.db2.mapper-locations}")
//    private String mapperLocations;
//
//    @Value("${mybatis.db2.type-aliases-package}")
//    private String typeAliasesPackage;
//    @Bean
//    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
//
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        // mybatis-config.xml이 필요하다면 아래 주석 해제
//        // sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
//        sqlSessionFactoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(mapperLocations));
//        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
//
//        return sqlSessionFactoryBean.getObject();
//    }
//    @Bean
//    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
