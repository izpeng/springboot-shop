package com.jt.controller.web;

import java.util.Date;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	//@RequestMapping("/web/testJSONP")
	public String test (String callback) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1L).setItemDesc("asdadadasdasd").setCreated(new Date()).setUpdated(new Date());
		String json = ObjectMapperUtil.toJSON(itemDesc);
		return callback+"("+json+")";

	}
	
	//api jsonp
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONP(String callback) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1L).setItemDesc("asdadadasdasd").setCreated(new Date()).setUpdated(new Date());
		return new JSONPObject(callback, itemDesc);
		
	}

}
