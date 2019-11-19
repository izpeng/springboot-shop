package com.jt.service;

import com.jt.pojo.User;

/**
 * 定义dubbo的UserService接口
 * @author Administrator
 *
 */
public interface DubboUserService {

	void insertUser(User user);

	String findUserByUP(User user, String ip);
	
}
