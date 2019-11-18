package com.jt.serviceimpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public boolean checkUser(String param, Integer type) {
		HashMap<Integer, String> columMap = new HashMap<>();
		columMap.put(1, "username");
		columMap.put(2, "phone");
		columMap.put(3, "email");
		String 	colum = columMap.get(type);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq(colum, param);
		User userDB = userMapper.selectOne(queryWrapper);
		return userDB==null?false:true;
	}

}
