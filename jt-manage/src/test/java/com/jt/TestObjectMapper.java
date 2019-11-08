package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class TestObjectMapper {
	private static final ObjectMapper Mapper = new ObjectMapper();
	@Test
	public void object2JSON() throws Exception {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L)
		.setItemDesc("<html>")
		.setCreated(new Date())
		.setUpdated(itemDesc.getCreated());
		String json = Mapper.writeValueAsString(itemDesc);
		System.out.println(json);
		
		ItemDesc readValue = Mapper.readValue(json, ItemDesc.class);
		System.out.println(readValue);
		
		
	}
	
	
	@Test
	public void list2JSON() throws IOException {
		List<ItemDesc> list = new ArrayList<>();
		ItemDesc itemDesc1 = new ItemDesc();
		itemDesc1.setItemId(1000L)
		.setItemDesc("<html>")
		.setCreated(new Date())
		.setUpdated(itemDesc1.getCreated());
		
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(1000L)
		.setItemDesc("<html>")
		.setCreated(new Date())
		.setUpdated(itemDesc2.getCreated());
		
		list.add(itemDesc1);
		list.add(itemDesc2);
		String json = Mapper.writeValueAsString(list);
		System.out.println(json);
		
		
		List ItemDescList = Mapper.readValue(json, list.getClass());
		System.out.println(ItemDescList);
	}
	
	
}
