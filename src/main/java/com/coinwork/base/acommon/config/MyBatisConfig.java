package com.coinwork.base.acommon.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.coinwork.base.**.mapper", sqlSessionTemplateRef = "mySqlSessionTemplate")
public class MyBatisConfig {

    @Bean(name="oracleSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "myDataSource") DataSource hikariDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(hikariDataSource);
        // jdbcTypeForNull 문제 해결. oracle null 데이터 입력시 문제.
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/config/mybatis-config.xml") );
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("oracleSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}