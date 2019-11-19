package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
/**
 * 获取cookie
 */
	public static Cookie getCookie(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies!=null && cookies.length>0) {
			for (Cookie cookie : cookies) {
				if(cookieName.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	public static Cookie deleteCookie(HttpServletResponse response,String cookieName,Integer maxAge,String domain,String path) {
		
		Cookie jtCookie = new Cookie(cookieName,"");
		jtCookie.setMaxAge(maxAge);
		jtCookie.setDomain(domain);
		jtCookie.setPath(path);
		return jtCookie;
		
	}
}
