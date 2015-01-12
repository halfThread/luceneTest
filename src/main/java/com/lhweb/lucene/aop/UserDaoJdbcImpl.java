package com.lhweb.lucene.aop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao{
	/**
	 * 从数据库中获取数据
	 */
	@Override
	public List<String> getUsersInfo() {
		List<String> users = new ArrayList<String>();
		for(int i = 0; i < 5; i++){
			users.add("JDBC_user_"+(i+1));
		}
		return users;
	}
}
