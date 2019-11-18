package com.jt.service;

import com.jt.pojo.User;

/*
 * 定义dubbo service接口
 */
public interface DubboUserService {

	void insertUser(User user);

	String findUserByUP(User user, String ipAddr);

}
