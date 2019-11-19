package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

//实现user业务逻辑功能  需要页面跳转
@Controller   
@RequestMapping("/user")
public class UserController {
	
	@Reference(check = false)
	private DubboUserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/{moduleName}")
	public String moduleName(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	
	//http://www.jt.com/user/doRegister
	//type:post
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		
		userService.insertUser(user);
		return SysResult.success();
	}
	
	/**
	 *   实现用户单点登录操作
	 *   1.动态获取IP地址
	 *   2.将ticket信息发送到Cookie中
	 *   
	 * Cookie用法:
	 * 	ticketCookie.setMaxAge(大于0); 存活时间单位秒
	 *  ticketCookie.setMaxAge(0);	     立即删除
	 *  ticketCookie.setMaxAge(-1);    会话关闭时,cookie删除
	 *  
	 *  2.关于path说明
	 *  url:www.jd.com/login.html
	 *  ticketCookie.setPath("/");
	 *  ticketCookie.setPath("/abc"); url无法访问该cookie
	 * @param user
	 * @return
	 */
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletRequest request,HttpServletResponse response) {
		
		//1.动态获取用户IP地址
		String ip = IPUtil.getIpAddr(request);
		//2.获取校验结果
		String ticket = userService.findUserByUP(user,ip);
		
		if(StringUtils.isEmpty(ticket)) {
			//表示用户名和密码错误
			return SysResult.fail();
		}
		
		//3.数据保存到cookie中
		Cookie ticketCookie = new Cookie("JT_TICKET", ticket);
		ticketCookie.setMaxAge(7*24*3600);
		ticketCookie.setPath("/");  //根目录有效
		//由于单点登录,需要将cookie信息设置为共享数据
		ticketCookie.setDomain("jt.com");
		response.addCookie(ticketCookie);
		return SysResult.success();
	}
	
	
	/**
	 *   实现用户登出操作
	 * 业务:
	 * 	1.删除redis中的数据  ticket
	 * 	2.删除cookie       "JT_TICKET"
	 *  
	 * 实现思路:
	 * 	1.先获取"JT_TICKET"cookie中的值!!
	 * 	2.删除redis数据
	 *  3.删除cookie记录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.获取Cookie信息
		Cookie cookie = 
				CookieUtil.getCookie(request, "JT_TICKET");
		if(cookie == null) {
			//重定向到系统首页
			return "redirect:/";
		}
		
		//2.删除redis中的数据
		String ticket = cookie.getValue();
		jedisCluster.del(ticket);
		
		//3.利用工具API实现Cookie删除
		CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");
		//重定向到系统首页
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
}
