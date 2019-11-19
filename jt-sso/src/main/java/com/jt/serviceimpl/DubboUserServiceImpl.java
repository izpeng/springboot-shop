package com.jt.serviceimpl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubboUserServiceImpl implements DubboUserService{
	@Autowired
	private UserMapper userMapper;
	@Autowired(required = false)
	private JedisCluster jedis;
	/**
	 * user
	 */
	@Override
	public void insertUser(User user) {
		String   md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

		user.setEmail(user.getPhone())
		.setPassword(md5Pass)
		.setCreated(new Date())
		.setUpdated(user.getCreated());
		userMapper.insert(user);
	}
	@Override
	public String findUserByUP(User user, String ipAddr) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);

		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		if(userDB==null) {
			return null;
		}
		String uuid = UUID.randomUUID().toString();
		String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());

		userDB.setPassword("123456你信吗?");
		String userJson = ObjectMapperUtil.toJSON(userDB);	
		//防止用户重复登录
		if(jedis.exists("JT_USERNAME_"+userDB.getUsername())) {
			String oldTicket = jedis.get("JT_USERNAME_"+userDB.getUsername());
			jedis.del(oldTicket);
		}
		jedis.hset(ticket, "JT_USER", userJson);
		jedis.hset(ticket, "JT_USER_IP", ipAddr);
		jedis.expire(ticket, 7*24*3600);
		
		jedis.setex("JT_USERNAME_"+userDB.getUsername(),7*24*3600, ticket);
		return ticket;
	}

}
