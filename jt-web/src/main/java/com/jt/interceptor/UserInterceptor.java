package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
@Component	//表示交给Spring容器管理
public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * boolean: true 放行   
	 * 			false 拦截 必须配合重定向使用.
	 * 业务思路:
	 * 		如何判断用户是否登录???
	 * 步骤:
	 * 		1.动态获取Cookie中的JT_TICKET中的值.
	 * 		2.获取用户的IP地址,校验数据.
	 * 		3.查询redis服务器是否有数据.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取Cookie数据
		Cookie cookie = CookieUtil.getCookie(request, "JT_TICKET");
		if(cookie !=null) {
			String ticket = cookie.getValue();
			if(!StringUtils.isEmpty(ticket)) {
				if(jedisCluster.exists(ticket)) {
					//2.校验IP
					String nowIP = IPUtil.getIpAddr(request);
					String realIP = jedisCluster.hget(ticket, "JT_USER_IP");
					if(nowIP.equals(realIP)) {
						
						String userJSON = jedisCluster.hget(ticket, "JT_USER");
						User user = ObjectMapperUtil.toObject(userJSON, User.class);
						request.setAttribute("JT_USER", user);
						//利用ThreadLocal方式动态获取数据
						UserThreadLocal.setUser(user);
						return true; //表示放行
					}
				}
			}
		}
		response.sendRedirect("/user/login.html");
		return false; //表示拦截
	}
	
	
	/**
	 * 在拦截器最后一步,实现数据清空 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		UserThreadLocal.remove();
	}
}
