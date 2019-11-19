package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	
	
	/**
	 * 程序链接哨兵入门案例
	 * masterName:mymaster
	 * sentinels: 哨兵的Set集合信息.
	 * 
	 * 端口说明:
	 * 	默认端口: 6379
	 * 	通信端口:16379 PING-PONG
	 * 	哨兵端口:26379
	 */
	
	@Test
	public void test01() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.175.129:26379");
		JedisSentinelPool pool = 
				new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("1907", "redis哨兵测试成功!!!!");
		System.out.println(jedis.get("1907"));
	}
	
	
	
	
	
}
