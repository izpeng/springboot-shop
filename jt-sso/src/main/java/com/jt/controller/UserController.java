package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/user")
public class UserController {
		
	@Autowired
	private UserService userService;
	/**
	 * 利用jsonp实现校验
	 */
	
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,@PathVariable Integer type,String callback) {
		boolean result=userService.checkUser(param,type);
		SysResult sysResult = SysResult.success(result);
		return new JSONPObject(callback, sysResult);
		
	}
	
	
	
	
	
	
}
