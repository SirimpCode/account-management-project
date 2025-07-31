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
//        basePackages = "com.github.jpaquerydslmybatis.repository.db1.mybatis",
//        sqlSessionFactoryRef = "db1SqlSessionFactory"
//)
//public class MybatisConfigDb1 {
//
//    @Value("${mybatis.db1.mapper-locations}")
//    private String mapperLocations;
//
//    @Value("${mybatis.db1.type-aliases-package}")
//    private String typeAliasesPackage;
//
//    @Bean
//    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
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
//    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
