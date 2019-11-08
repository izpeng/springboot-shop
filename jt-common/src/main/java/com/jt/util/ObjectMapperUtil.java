package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	
	
	private static final ObjectMapper Mapper = new ObjectMapper();
	public static String toJSON(Object obj) {
		String json =null;
		try {
			json = Mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return json;

	}
	
	
	
	public static  final <T> T toObject(String json, Class<T> targetClass) {
		T obj =null;
		try {
			 obj = Mapper.readValue(json, targetClass);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return (T) obj;
		
		
	}
	
}
