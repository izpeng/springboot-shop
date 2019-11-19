package com.jt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	private JedisCluster jedisCluster;
	
	/**
	 * 利用JSONP实现用户信息校验
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(
			@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		//校验用户信息 
		boolean result = userService.checkUser(param,type);
		SysResult sysResult = SysResult.success(result);
		return new JSONPObject(callback, sysResult);
	}
	
	
	/**
	 *  根据ticket信息检索用户信息.
	 *  实现思路:
	 *  1.动态获取用户的ticket
	 *  2.查询redis缓存.
	 *  	2.1 如果没有数据  返回201等信息
	 *  	2.2 校验用户的IP是否正确
	 *  	2.3 如果IP校验通过 则表示用户正常登陆.
	 * @return
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(
			@PathVariable String ticket,
			String callback,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		JSONPObject jsonpObject;
		//1.判断ticket是否为null
		if(StringUtils.isEmpty(ticket)) {
			jsonpObject = new JSONPObject(callback, SysResult.fail());
			return jsonpObject;
		}
		
		//2.判断当前key是否存在
		if(!jedisCluster.exists(ticket)) {
			//如果ticket不为null,但是redis中没有改数据.
			//则表示Cookie中的数据有误,应该删除该cookie
			CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");
			jsonpObject = new JSONPObject(callback, SysResult.fail());
			return jsonpObject;
		}
		
		//3.表示ticket数据存在. 检验IP地址是否正确
		String nowIP = IPUtil.getIpAddr(request);
		String realIP = jedisCluster.hget(ticket, "JT_USER_IP");
		if(!nowIP.equals(realIP)) {
			jsonpObject = new JSONPObject(callback, SysResult.fail());
			return jsonpObject;
		}
		
		//4.说明用户信息是正确的.返回用户JSON数据
		String userJSON = jedisCluster.hget(ticket, "JT_USER");
		jsonpObject = 
				new JSONPObject(callback, SysResult.success(userJSON));
		return jsonpObject;
	}
	
}
