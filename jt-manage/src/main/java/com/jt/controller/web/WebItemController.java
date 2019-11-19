package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.pojo.TestJSON;
import com.jt.service.ItemService;

@RestController	//返回数据都是JSON串
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
	
	//http://manage.jt.com/web/item/findItemById?itemId=562379
	@RequestMapping("/findItemById")
	public Item findItemById(Long itemId) {
		
		return itemService.findItemById(itemId);
	}
	
	//查询商品详情信息
	@RequestMapping("/findItemDescById")
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemService.findItemDescById(itemId);
	}
	
	//{id:110,name:"xxx",age:18}
	@RequestMapping("/testJSON")
	public TestJSON json() {
		TestJSON json = new TestJSON();
		json.setId(110);
		json.setName("警察局");
		json.setAge(18);
		return json;
	}
	
	
	
	
}
