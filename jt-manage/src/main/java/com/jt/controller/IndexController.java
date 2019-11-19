package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	
	/**
	 * 实现页面跳转
	 * 1.url:/page/item-add
	 * 2.url:/page/item-list
	 * 
	 * 特点: 
	 * 	1.前缀/page相同
	 * 	2.访问地址不同
	 *  3.请求地址与跳转的页面相同的
	 * 
	 *  想法:能否动态接收url中数据当做参数??
	 * 用法:
	 * 	1.参数使用/分割
	 *  2.参数使用{}包裹,并且设置变量名称
	 *  3.@PathVariable注解动态获取数据
	 *     一般情况下,参数与url中参数名称必须一致
	 *  
	 *     补充:如果传递的参数数量众多时,使用对象封装
	 *     
	 */
	@RequestMapping("/page/{moduleName}")
	public String item_add(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	
	
	
	/**
	 * restFul风格:
	 *  	用户请求地址尽可能不变,只改变请求的类型,
	 *  从而实现请求的操作.
	 *  
	 *  url:localhost/user type:post   增
	 *  url:localhost/user type:put    改
	 *  url:localhost/user type:delete 删
	 *  url:localhost/user type:get    查
	 */
	
}
