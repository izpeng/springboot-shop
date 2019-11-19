package com.jt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedis;
	/**
	 * 利用jsonp实现校验
	 */
	
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,@PathVariable Integer type,String callback) {
		boolean result=userService.checkUser(param,type);
		SysResult sysResult = SysResult.success(result);
		return new JSONPObject(callback, sysResult);
		
	}
	
	//http://sso.jt.com/user/query/226eb298dd5e7a7422b1177fe23a71ca?callback=jsonp1574039817351&_=1574039817412
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,
			String callback,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if(StringUtils.isEmpty(ticket)) {
			return new JSONPObject(callback, SysResult.fail());
		}
		if(!jedis.exists(ticket)) {		
			CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");
			return new JSONPObject(callback, SysResult.fail());	
		}
		String realIP = jedis.hget(ticket, "JT_USER_IP");
	
		String nowIP = IPUtil.getIpAddr(request);
		if(!nowIP.equals(realIP)) {
			return new JSONPObject(callback, SysResult.fail());
		}
		
		String userJSON = jedis.hget(ticket, "JT_USER");
		
		return new JSONPObject(callback, SysResult.success(userJSON));
		
	}
	
	
}
