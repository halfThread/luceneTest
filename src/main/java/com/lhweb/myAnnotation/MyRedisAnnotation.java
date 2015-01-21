package com.lhweb.myAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义redis注释
 * 
 * @Target(ElementType.METHOD) ：该注释用户注释方法
 * @Retention(RetentionPolicy.RUNTIME) : 运行时有效
 * @Documented 在默认的情况下javadoc命令不会将我们的annotation生成再doc中去的，
 *             所以使用该标记就是告诉jdk让它也将annotation生成到doc中去
 * @Inherited 比如有一个类A，在他上面有一个标记annotation，那么A的子类B是否
 * 			   不用再次标记annotation就可以继承得到呢，答案是肯定的
 * @author liuhuan 2015年1月12日 下午4:54:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
public @interface MyRedisAnnotation {
	String myValue() default "myRedis";
}
