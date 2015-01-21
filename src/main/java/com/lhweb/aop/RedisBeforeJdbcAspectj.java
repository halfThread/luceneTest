package com.lhweb.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * 切面类，将redisDao 切入到jdbcDao前面执行
 * 
 * @author liuhuan
 *
 */
@Component("redisBeforeJdbcAspectj")
public class RedisBeforeJdbcAspectj {
	
	 @Resource(name="UserDaoRedisImpl")
	 private UserDao userDao;

	public void doBefore(JoinPoint point) {
		System.out.println("Begining method:"
				+ point.getTarget().getClass().getName() + "."
				+ point.getSignature().getName());
	}

	public void doAfter(JoinPoint point) {
		System.out.println("Ending method:"
				+ point.getTarget().getClass().getName() + "."
				+ point.getSignature().getName());
	}

	public Object doAround(ProceedingJoinPoint ppoint) throws Throwable {
		System.out.println("执行doAround");
//		String className = ppoint.getClass().getName();
		String methodName = ppoint.getSignature().getName();
		
		Class target = userDao.getClass();
		Method method = target.getMethod(methodName);
		
		List<String> users = (List<String>) method.invoke(userDao);
		if(users==null || users.size()==0){
			return ppoint.proceed();
		}else{
			return users;
		}
	}
}
