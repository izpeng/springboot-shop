package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Component	//将类交给容器管理
@Aspect		//标识切面
public class CacheAspect {
	//说明:当tomcat服务器启动时,会启动spring容器要求立即注入对象
	//如果项目中有可能不需要该对象时,可以配置required = false 懒加载
	@Autowired(required = false)
	private JedisCluster jedis; //连接redis集群
	//private Jedis jedis;	//链接哨兵机制
	//private ShardedJedis jedis;
	//private Jedis jedis; //单台  分片方式实现

	//如果是环绕通知,则参数必须写ProceedingJoinPoint,必须位于第一位
	@SuppressWarnings("unchecked")
	@Around("@annotation(cacheFind)")
	public Object around(ProceedingJoinPoint joinPoint,CacheFind cacheFind) {
		
		Object obj = null;
		String key = getKey(joinPoint,cacheFind);
		
		//1.先查询缓存数据
		String result = jedis.get(key);
		try {
			
			if(StringUtils.isEmpty(result)) {
				//第一次查询数据
				obj = joinPoint.proceed();	//执行目标方法
				//将数据保存到redis中
				String json = ObjectMapperUtil.toJSON(obj);
				//判断用户是否传递超时时间
				if(cacheFind.seconds()==0) 
					jedis.set(key, json);
				else 
					jedis.setex(key, cacheFind.seconds(), json);
				
				System.out.println("执行数据库查询");
			}else {
				//数据不为null,将缓存数据转化为对象
				Class<?> returnType = getType(joinPoint);
				obj = ObjectMapperUtil.toObject(result,returnType);
				System.out.println("执行AOP缓存!!!!");
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return obj;
	}
	
	/**
	 * 目的:获取方法的返回值类型
	 * 提示:利用方法对象获取返回值类型
	 * @param joinPoint
	 * @return
	 */
	private Class getType(ProceedingJoinPoint joinPoint) {
		
		MethodSignature signature = 
				(MethodSignature) joinPoint.getSignature();
		
		return signature.getReturnType();
		
		/*
		 * Object[] objs = joinPoint.getArgs(); Class[] argsClass = new
		 * Class[objs.length]; for (int i=0; i<objs.length;i++) { argsClass[i] =
		 * objs[i].getClass(); }
		 * 
		 * joinPoint.getTarget().getClass().getMethod(name, parameterTypes);
		 */
	}

	//判断用户是否传递参数, 如果用户传参使用用户自己的key.
	//如果用户没有指定参数,使用动态生成的.
	private String getKey(ProceedingJoinPoint joinPoint, CacheFind cacheFind) {
		//获取当前方法的名称 类名.方法名
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		String key = cacheFind.key(); 

		if(!StringUtils.isEmpty(key)) { //以用户的数据为准

			return className+"."+methodName+"::"+key;
		}else {//动态拼接参数
			//类名.方法名::第一个参数值
			Object args0 = joinPoint.getArgs()[0];
			return className+"."+methodName+"::"+args0;
		}
	}

}
