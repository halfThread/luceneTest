package com.lhweb.lucene.myAnnotation;

import java.util.List;

/**
 * 
 * @author liuhuan
 *2015年1月12日 下午4:49:10
 */
public class MyAnnotationImpl {
	
	@MyRedisAnnotation(myValue = "helloWorld")
	public List<String> getUsers(String value){
		
		return null;
	}
}
