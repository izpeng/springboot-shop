package com.jt.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController  //保证返回值数据都是JSON时使用
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 *    根据条件查询数据信息.
	 *  url:http://localhost:8091/item/query?page=1&rows=20
	 */
	@RequestMapping("/query")
	public EasyUITable findItemByPage
						(Integer page,Integer rows) {
		
		return itemService.findItemByPage(page,rows);
	}
	
	
	/**
	 *  实现商品新增
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
	}
	
	/**
	 * 实现商品更新
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	
	
	
	/**
	 *   商品下架
	 * url:/item/instock
	 * type:post
	 * params:{ids:111,222}
	 */
	@RequestMapping("instock")
	public SysResult instockItem(Long[] ids) {
		
		int status = 2;	//表示下架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	
	/**
	 *   商品上架
	 * url:/item/reshelf
	 * type:post
	 * params:{ids:111,222}
	 */
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		
		int status = 1;	//表示上架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	
	
	@RequestMapping("delete")
	public SysResult deleteItem(Long[] ids) {
		
		itemService.deleteItem(ids);
		return SysResult.success();
	}
	
	/**
	 * 根据Id查询商品详情信息
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		
		ItemDesc desc = 
			itemService.findItemDescById(itemId);
		
		return SysResult.success(desc);
		//{status:200,msg:'',data:{itemId:"1123123",itemDesc:"html代码"}}
	}
	
	
	
	
	
}
