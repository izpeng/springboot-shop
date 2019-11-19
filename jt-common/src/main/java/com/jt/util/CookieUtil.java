package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 1.获取Cookie对象
	 * @param request
	 * @param cookieName
	 * @return
	 * bug:一般不会出现,但是在特定的业务环境下出现的异常
	 */
	public static Cookie getCookie(HttpServletRequest request,
			String cookieName) {
		Cookie[] cookies = request.getCookies();
		//校验cookie是否有效.
		if(cookies !=null && cookies.length>0) {
			for (Cookie cookie : cookies) {
				if(cookieName.equals(cookie.getName())) {

					return cookie;
				}
			}
		}
		return null;
	}

	public static void deleteCookie(HttpServletResponse response,String cookieName,int maxAge,String domain,String path) {
		 Cookie jtCookie = new Cookie(cookieName,""); 
		 jtCookie.setMaxAge(maxAge); //表示立即删除
		 jtCookie.setDomain(domain);//设置cookie共享 
		 jtCookie.setPath(path);
		 response.addCookie(jtCookie);
	}
}
