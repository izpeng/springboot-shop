package com.jt;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	//利用SpringBoot的测试规则时才会添加测试注解

	public Jedis jedis;

	//说明:添加before注解的作用,当@Test注解执行时,要
	//先执行before方法
	@Before
	public void init() {
		String host = "192.168.175.129";
		int port = 6379;
		jedis = new Jedis(host, port);
	}



	/**
	 * spring连接redis入门案例
	 *  1.检查redis配置文件 3处 
	 *  2.redis-server redis.conf
	 * @throws InterruptedException 
	 */
	@Test
	public void testString() throws InterruptedException {
		String host = "192.168.175.129";
		int port = 6379;
		Jedis jedis = new Jedis(host, port);
		//1.set方式
		jedis.set("1709","redis的入门案例!!!!!");
		System.out.println(jedis.get("1709"));

		//2.批量操作
		jedis.mset("a","a","b","b");
		System.out.println(jedis.mget("a","b"));

		//3.清空数据库
		jedis.flushDB();

		//4.自增
		jedis.set("num", "1");
		Long result = jedis.incr("num");
		System.out.println("获取自增的结果:"+result);

		//5.设定超时时间
		jedis.expire("num", 30);
		Thread.sleep(2000);
		System.out.println(jedis.ttl("num"));

		//6.撤销超时时间
		jedis.persist("num");	
	}



	/**
	 * 	需求:如果redis中已经存在该key则不允许修改.
	 */
	@Test
	public void testString2() {
		if(!jedis.exists("abc")) {
			jedis.set("abc", "aaaa");
			System.out.println("入库成功!!!!");
		}else {
			System.out.println("该数据已经存在");
		}

		//利用redisAPI实现上述功能
		Long result = jedis.setnx("aaa", "bbbb");
		System.out.println("获取返回值结果:"+result);
	}
	
	/**
	 *  1.为数据添加超时时间 原子性操作
	 *  2.set数据不能修改,同时要设定超时时间
	 *  
	 */
	@Test
	public void testString3() {
		jedis.setex("time", 10, "asdf");
		//jedis.psetex(key, milliseconds, value)
		//NX:不允许修改  XX 可以修改    EX 秒  PX 毫秒
		jedis.set("1907", "设定redis值", "NX", "EX", 10);
	}
	
	
	/**
	 * 测试hash类型
	 */
	@Test
	public void testHash() {
		jedis.hset("user", "id", "1001");
		jedis.hset("user", "name", "仓老师");
		jedis.hset("user", "age", "18");
		jedis.hset("user", "sex", "男");
		System.out.println(jedis.hvals("user"));
		System.out.println(jedis.hkeys("user"));
		System.out.println(jedis.hgetAll("user"));
	}
	
	/**
	 * 当做消息队列使用
	 * 2 3 4 5 1 2 3 4 5
	 * 第一次: 1
	 * 第二次:2
	 */
	@Test
	public void testList() {
		jedis.lpush("list", "1","2","3","4","5");
		System.out.println(jedis.rpop("list"));
	}


}
