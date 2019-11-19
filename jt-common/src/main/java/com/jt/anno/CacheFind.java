package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)	//对方法生效
@Retention(RetentionPolicy.RUNTIME) //运行时有效
public @interface CacheFind { 
	
	//1.key可以动态获取. 类名.方法名::第一个参数值
	//2.key也可以自己指定.
	String key()  default "";
	int seconds() default 0;	//用户数据不超时
	
}









