package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

//JSONP要求返回的数据都是JSON
@RestController
public class JSONPController {
	
	//约定回调函数名称 callback
	//@RequestMapping("/web/testJSONP")
	public String testJSONPOld(String callback) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(10001L)
				.setItemDesc("商品详情信息!!!!!!");
		String json = ObjectMapperUtil.toJSON(itemDesc);
		return callback+"("+json+")";
	}
	
	//利用API实现JSONP跨域访问
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONP(String callback) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(10001L)
				.setItemDesc("商品详情信息!!!!!!");
		return new JSONPObject(callback, itemDesc);
	}
	
	
	
	
	
	
	
	
	
}
