package com.lhweb.myAnnotation;


/**
 * 
 * @author liuhuan
 *2015年1月12日 下午4:49:10
 */
public class MyAnnotationImpl {
	
	@MyRedisAnnotation
	public void getUsers(String value){
		System.out.println(value);
	}
}
