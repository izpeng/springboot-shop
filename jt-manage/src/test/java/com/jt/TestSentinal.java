package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinal {
/**
 * 哨兵
 */@Test
	public void test01() {
		Set<String > sentinels =new HashSet<>();
		sentinels.add("192.168.161.129:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		
		Jedis jedis = pool.getResource();
		jedis.set("1234", "大师傅");
		System.out.println(jedis.get("1234"));
	} 

}
