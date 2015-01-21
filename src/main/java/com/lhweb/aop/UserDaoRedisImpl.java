package com.lhweb.aop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("UserDaoRedisImpl")
public class UserDaoRedisImpl implements UserDao{

	@Override
	public List<String> getUsersInfo() {
		List<String> users = new ArrayList<String>();
		for(int i = 0; i < 5;i++){
			users.add("redis_user_"+(i+1));
		}
		return users;
	}

}
