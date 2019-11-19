package com.jt;

import org.junit.Test;

import com.jt.pojo.TestJSON;
import com.jt.util.ObjectMapperUtil;

public class TestJSON2 {
	
	@Test
	public void testJSON2Object() {
		String json = "{\"id\":110,\"name\":\"警察局\",\"age\":18,\"xxx\":\"我是谁?? ..........   臭臭泥\"}";
		TestJSON testJSON = 
				ObjectMapperUtil.toObject(json, TestJSON.class);
		System.out.println(testJSON);
	}
}
