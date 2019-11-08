package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestShards {
	@Test
	public void  test01() {
		String host = "192.168.161.129";
		List<JedisShardInfo> shards = new ArrayList<>();
		shards.add(new JedisShardInfo(host, 6379));
		shards.add(new JedisShardInfo(host, 6380));
		shards.add(new JedisShardInfo(host, 6381));
		ShardedJedis jedis = new ShardedJedis(shards);

		jedis.set("1907", "学习redis分片机制");
	}
}
