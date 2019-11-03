package com.jt.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/query")
	public EasyUITable findItemByPage( Integer page, Integer rows) {
		
		return itemService.findItemByPage(page,rows);
		
	}
	
	/**
	 * 商品新增
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
			itemService.saveItem(item,itemDesc);
			return 	SysResult.success();
	}
	
	
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		//itemDesc.setItemId(item.getId());
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
		
	}
	
	
	@RequestMapping("/instock")
	public SysResult instockItem(Long[] ids) {
		Integer status = 2;
		itemService.updateStatus(ids,status);
		return SysResult.success();
		
	}
	
	
	
	@RequestMapping("/reshelf")
	
	public SysResult reshelfItem(Long[] ids) {
		Integer status = 1;
		itemService.updateStatus(ids,status);
		return SysResult.success();
		
	}
	
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids) {

		itemDescService.deleteItemDescByIds(ids);
		itemService.deleteItem(ids);
		return SysResult.success();
		
	}
	
	
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(ItemDesc itemDesc) {
		itemDesc=itemService.findItemDescById(itemDesc.getItemId());
				return SysResult.success(itemDesc);

	}
}
