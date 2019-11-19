package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestShards {
	
	/**
	 * 测试redis分片
	 * 思考:面试了解redis分片原理吗??? 了解hash一致性规则吗?
	 */
	@Test
	public void test01() {
		String host = "192.168.175.129";
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(host, 6379));
		shards.add(new JedisShardInfo(host, 6380));
		shards.add(new JedisShardInfo(host, 6381));
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1907", "学习redis分片机制");
		System.out.println(jedis.get("1907"));
	}
	
}
