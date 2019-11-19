package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 	需要将json串与对象实现互转
 * @author Administrator
 *
 */
public class ObjectMapperUtil {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public static String toJSON(Object obj) {
		String json = null;
		try {
			json = MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return json;
	}
	
	//将json串转化为对象
	public static <T> T toObject(String json,Class<T> targetClass) {
		T obj = null;
		try {
			obj = MAPPER.readValue(json, targetClass);
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return obj;
	}
	
	
	
	
	
	
	
	
	
	
	
}
