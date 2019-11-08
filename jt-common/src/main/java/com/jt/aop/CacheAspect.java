package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

@Component
@Aspect
public class CacheAspect {
	@Autowired
	private Jedis jedis;

	//如果是环绕通知 必须写ProceedingJoinPoint  因为里边有proceed
	@Around("@annotation(cacheFind)")
	public Object arround(ProceedingJoinPoint joinPoint,CacheFind cacheFind){
		
		String key = getKey(joinPoint,cacheFind);
		String result = jedis.get(key);
		Object obj = null;
		
		if(StringUtils.isEmpty(result)) {
			try {
				obj =joinPoint.proceed();
				String json= ObjectMapperUtil.toJSON(obj);
				
				if(cacheFind.seconds()==0) {
					jedis.set(key, json);
				}else {
					int seconds = cacheFind.seconds();
					jedis.setex(key, seconds, json);
				}
				System.out.println("走的数据库");
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}else {
			Class  returnType = getType(joinPoint);
			
			obj = ObjectMapperUtil.toObject(result,returnType );
			System.out.println("走的缓存");
		}
		return obj;


	}

	private Class getType(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}

	private String getKey(ProceedingJoinPoint joinPoint, CacheFind cacheFind) {
		//
		String key = cacheFind.key();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		if(!StringUtils.isEmpty(key)) {
			//以用户数据为准
			return className+"."+methodName+"::"+key;
		}else {
			Object[] args = joinPoint.getArgs();
			
			return className+"."+methodName+"::"+args[0];
		}
		
	}
}
