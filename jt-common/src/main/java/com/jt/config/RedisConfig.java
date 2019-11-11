package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * spring容器管理配置类
 * @author 000
 *
 */

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

	/**
	 * jedis
	 */
	//	@Value("${redis.host}")
	//	private String host;
	//	@Value("${redis.port}")
	//	private int port;
	//	@Bean
	//	@Scope("prototype")   //设置为多例  当用户使用时创建
	//	public Jedis jedis() {
	//		Jedis jedis = new Jedis(host,port);
	//		return jedis;
	//	}


	/**
	 * 分片
	 */
	//	@Value("${redis.nodes}")
	//	private String nodes;
	//
	//	@Bean
	//	@Scope("prototype")   //设置为多例  当用户使用时创建
	//	public ShardedJedis shardsJedis() {
	//
	//
	//		List<JedisShardInfo> shards = new ArrayList<>();
	//		String[] redisNodes = nodes.split(",");
	//		for (String redisNode : redisNodes) {
	//			String[] hostAndPort = redisNode.split(":");
	//			String host = hostAndPort[0];
	//			int port =Integer.parseInt(hostAndPort[1]);
	//			JedisShardInfo jedisShardInfo = new JedisShardInfo(host,port );
	//			shards.add(jedisShardInfo);
	//		}
	//
	//		ShardedJedis shardsJedis = new ShardedJedis(shards);
	//
	//		return shardsJedis;
	//	}


	/**
	 * 哨兵
	 */
	//	@Value("${redis.sentinels}")
	//	private String sentinels;
	//	@Bean
	//	public JedisSentinelPool pool() {
	//		Set<String > set =new HashSet<>();
	//		set.add(sentinels);
	//		return new JedisSentinelPool("mymaster", set);
	//	}
	//	
	//	//规则:可以为bean方法自动注入对象
	//	@Bean
	//	@Scope("prototype")
	//	public Jedis jedis(JedisSentinelPool pool) {
	//		
	//		return pool.getResource();
	//	
	//		
	//		
	//	}


	/**
	 * 集群
	 * redis.nodes
	 */
	@Bean("redisSet")
	public Set<HostAndPort> redisSet(){
		String[] split = redisNodes.split(",");
		Set<HostAndPort> redisSet =new HashSet<>();
		for (String node : split) {
			String host= node.split(":")[0];
			int port=Integer.parseInt(node.split(":")[1]) ;
			redisSet.add(new HostAndPort(host, port));
		}
		return redisSet;
	}




	@Value("${redis.nodes}")
	private String redisNodes;
	@Bean
	@Scope("prototype")
	public JedisCluster jedisCluster(@Qualifier("redisSet")Set<HostAndPort> redisSet) {
		JedisCluster jedisCluster = new JedisCluster(redisSet);
		return jedisCluster;
	}


}
