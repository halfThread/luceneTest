package com.lhweb.lucene.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lhweb.aop.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class RedisBeforeJdbcAopTest {

	@Resource(name = "UserDaoJdbcImpl")
	private UserDao userDao;

	@Test
	public void getUserInfoFromDBTest() {
		List<String> users = userDao.getUsersInfo();
		System.out.println("============测试方法打印用户信息================");
		for (String user : users) {
			System.out.println(user);
		}
	}
}
