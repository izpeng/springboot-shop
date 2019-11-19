package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;

@Configuration	//表示配置类
public class MvcConfigurer implements WebMvcConfigurer{
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
	
	/**
	 * 添加用户的拦截器
	 * 
	 * url:localhost:8090/addUser/a/b
	 * /** 表示拦截所有(多级)的请求    对
	   /*  表示拦截一级目录请求	      错
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(userInterceptor)
		.addPathPatterns("/cart/**","/order/**");
	}
}
