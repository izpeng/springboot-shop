package com.jt;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	/**
	 * spring 连接redis
	 * @throws InterruptedException 
	 */
	@Test
	public void testString() throws InterruptedException {

		//jedis.set("asdasd", "入门案例");
		//System.out.println(jedis.get("asdasd"));

		//String mset = jedis.mset("1","2","3","4");
		//System.out.println(mset);

		//String flushDB = jedis.flushDB();
		//System.out.println(flushDB);

		//		String set = jedis.set("num", "1");
		//		Long incr = jedis.incr("num");
		//		System.out.println(incr);

		//		String mset = jedis.mset("a","a","b","b","c","c");
		//		System.out.println(mset);
		//		List<String> mget = jedis.mget("a","b","c");
		//		System.out.println(mget);
		//		

		//		Set<String> keys = jedis.keys("*");
		//		System.out.println(keys);
		//
		//		Long expire = jedis.expire("num", 30);
		//		Thread.sleep(2000);
		//		System.out.println(jedis.ttl("num"));
		//		
		//		jedis.persist("num")

	}

	public Jedis jedis;
	@Before    //before 当@test注解执行时先执行before
	public void init() {
		String host = "192.168.161.129";
		int port = 6379;
		jedis = new Jedis(host,port);
	}

	

	@Test
	public void testString2() {

		String str = "num";
		if(jedis.exists(str)) {
			System.out.println("存在");
		}else {
			jedis.set(str, "444");
			System.out.println("成功");
		}

		Long setnx = jedis.setnx("num", "SADSAD");
		System.out.println(setnx);



	}


	@Test
	public void testString3() {
//		jedis.set("time", "abc");
//		jedis.expire("time", 10);
		
		jedis.setex("time",10,"abc");
		//"NX" 不允许修改  "XX" 可以修改  EX 秒 PX 毫秒
		jedis.set("1907", "sss", "NX", "EX", 10);
		
		jedis.persist("1907");
		
		
	}



	@Test
	public void testHash() {
		jedis.hset("user", "id", "122");
		jedis.hset("user", "name", "sada");
		System.out.println(jedis.hvals("user"));
		System.out.println(jedis.hkeys("user"));
		System.out.println(jedis.hgetAll("user"));
		
	}



	@Test
	public void testList() {
		jedis.lpush("list", "1","2","3","4","5");
		System.out.println(jedis.rpop("list"));
		
	}











}

