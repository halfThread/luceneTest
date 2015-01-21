package com.lhweb.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
/**
 * AOP切面的注解实现
 * @author liuhuan
 *
 */
@Component
@Aspect
public class RedisBeforeJdbcAnnoAspectj {
	
	@Resource(name="UserDaoRedisImpl")
	private UserDao userDao;
	
	//切面
	public static final String pointcut = "execution(* com.lhweb.lucene.aop.UserDaoJdbcImpl*.*(..))";
	
	@Before(pointcut)
	public void doBefore(JoinPoint point) {
		System.out.println("Begining method:"
				+ point.getTarget().getClass().getName() + "."
				+ point.getSignature().getName());
	}

	@After(pointcut)
	public void doAfter(JoinPoint point) {
		System.out.println("Ending method:"
				+ point.getTarget().getClass().getName() + "."
				+ point.getSignature().getName());
	}
	
	@Around(pointcut)
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
