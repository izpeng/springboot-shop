package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheFind {
	//key可以动态获取  类名.方法名.第一个参数值
	//key也可以自己指定
	String key() default "";
	int seconds() default 0;   //用户数据不超时
}
