package com.pachiraframework.oauth2.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 系统数据源配置
 * @author KevinWang
 *
 */
@Configuration
public class DataSourceConfig {
	
	@Bean("oauth2DataSource")
	public DataSource oauth2DataSource(){  
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc://localhost:3306/pachira_oauth2?serverTimezone=GMT%2B8&characterEncoding=utf-8 ");
		dataSource.setUsername("root");
		dataSource.setPassword("111111");
		return dataSource;
	}
	@Bean("oauth2SessionFactory")
    public SqlSessionFactory oauth2SessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(oauth2DataSource()); // 使用titan数据源, 连接titan库
        return factoryBean.getObject();
    }
}
