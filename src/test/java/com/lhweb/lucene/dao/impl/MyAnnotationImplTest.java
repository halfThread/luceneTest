package com.lhweb.lucene.dao.impl;

import java.lang.reflect.Method;

import org.junit.Test;

import com.lhweb.lucene.myAnnotation.MyAnnotationImpl;
import com.lhweb.lucene.myAnnotation.MyRedisAnnotation;

public class MyAnnotationImplTest {

	@Test
	public void test() throws Exception {
		MyAnnotationImpl myAnnotation = new MyAnnotationImpl();	// 初始化一个实例，用于方法调用
		
		Method[] methods = MyAnnotationImpl.class.getDeclaredMethods(); // 获得所有方法
		
		for(Method method:methods){
			MyRedisAnnotation myRedis = null;
			if((myRedis = method.getAnnotation(MyRedisAnnotation.class)) != null){// 检测是否使用了我们的注解
				method.invoke(myAnnotation,myRedis.myValue()); // 如果使用了我们的注解，我们就把注解里的"paramValue"参数值作为方法参数来调用方法
			} else{
				 method.invoke(myAnnotation, "OtherValue"); // 如果没有使用我们的注解，我们就需要使用普通的方式来调用方法了
			}
		}
		
	}

}
