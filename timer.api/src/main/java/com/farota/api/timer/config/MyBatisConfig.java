package com.farota.api.timer.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.farota.core.database.DsBiz;


public abstract class MyBatisConfig {
	public static final String CONFIG_LOCATION_PATH = "mybatis-config.xml";
	
	// biz database
	public static final String BIZ_BASE_PACKAGE = "com.farota.api.timer.mapper.biz";
	public static final String BIZ_TYPE_ALIASES_PACKAGE = "com.farota.api.timer";
	public static final String BIZ_MAPPER_LOCATIONS_PATH = "/sqlmap/biz/**/*.xml";
	
	protected void bizConfigureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
		PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setTypeAliasesPackage(BIZ_TYPE_ALIASES_PACKAGE);
		sessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_LOCATION_PATH));
		sessionFactoryBean.setMapperLocations(pathResolver.getResources(BIZ_MAPPER_LOCATIONS_PATH));
	}
}

@Configuration
@MapperScan(basePackages = MyBatisConfig.BIZ_BASE_PACKAGE, annotationClass = DsBiz.class, sqlSessionFactoryRef = "bizSqlSessionFactory")
class BizMyBatisConfig extends MyBatisConfig {

	@Bean
	public SqlSessionFactory bizSqlSessionFactory(@Qualifier("bizDataSource") DataSource bizDataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		bizConfigureSqlSessionFactory(sessionFactoryBean, bizDataSource);
		return sessionFactoryBean.getObject();
	}
}