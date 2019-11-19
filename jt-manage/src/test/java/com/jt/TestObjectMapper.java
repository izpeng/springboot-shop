package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

public class TestObjectMapper {
	
	private static final ObjectMapper MAPPER = 
								new ObjectMapper();
	
	/**
	 * 实现对象和JSON数据的转化
	 * @throws IOException 
	 */
	@Test
	public void object2JSON() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L)
				.setItemDesc("<html>")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		String json = MAPPER.writeValueAsString(itemDesc);
		System.out.println(json);
		
		//2.将JSON还原对象  src:json数据
		ItemDesc itemDesc2 = 
					MAPPER.readValue(json, ItemDesc.class);
		System.out.println(itemDesc2.toString());
	}
	
	/**
	 * 实现List集合转化
	 * @throws IOException 
	 */
	@Test
	public void list2JSON() throws IOException {
		List<ItemDesc> list = new ArrayList<ItemDesc>();
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L)
				.setItemDesc("<html>")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(1000L)
				.setItemDesc("<html>")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		list.add(itemDesc);
		list.add(itemDesc2);
		String json = MAPPER.writeValueAsString(list);
		System.out.println(json);
		
		//2.将JSON转化为对象
		List itemList = MAPPER.readValue(json, list.getClass());
		System.out.println(itemList);
		
	}
	
}
