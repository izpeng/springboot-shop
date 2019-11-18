package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findItemById")
	public Item findItemById(Long itemId) {
		Item item = itemService.findItemById(itemId);

		return item;

	}

	@RestController
	@RequestMapping("/web/item")
	public class WebItemDescController {
		
		@RequestMapping("/findItemDescById")
		public ItemDesc findItemById(Long itemId) {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return itemDesc;

		}
	}
}