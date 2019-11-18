package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.IPUtil;
/**
 * 登录注册
 * @author 000
 *
 */
import com.jt.vo.SysResult;
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Reference(check = false)
	private DubboUserService dubboUserService;
	
	@RequestMapping("/{moduleName}")
	public String moduleName(@PathVariable String moduleName) {
		return moduleName;
	}
	//http://www.jt.com/user/doRegister
	//注册
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		dubboUserService.insertUser(user);
		
		return SysResult.success();
	}
	
	
	//单点登录
	//http://www.jt.com/user/doLogin?r=0.35219756652542
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletRequest request,HttpServletResponse response ) {
		//动态获取ip
		String ipAddr = IPUtil.getIpAddr(request);

		String ticket = dubboUserService.findUserByUP(user,ipAddr);
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}else {
			//cookie 数据保存到cookie
			Cookie ticketCookie = new Cookie("JT_TICKET", ticket);
			ticketCookie.setMaxAge(7*24*3600);
			ticketCookie.setPath("/");
			//将cookie设为共享
			ticketCookie.setDomain("jt.com");
			response.addCookie(ticketCookie);
			return SysResult.success();
		}

		
	}
	
	
	
	
	
}
