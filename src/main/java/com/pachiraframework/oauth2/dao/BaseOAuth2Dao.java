package com.pachiraframework.oauth2.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pachiraframework.dao.BaseDao;

/**
 * 连接oauth2数据库操作的dao
 * 
 * @author KevinWang
 *
 */
public abstract class BaseOAuth2Dao extends BaseDao {

	@Override
	public void setSqlSessionFactory(
			@Autowired @Qualifier("oauth2SessionFactory") SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

}
