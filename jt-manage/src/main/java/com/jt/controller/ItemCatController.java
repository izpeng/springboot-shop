package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 根据itemCatId查询商品分类名称
	 * http://localhost:8091/item/cat/queryItemName?itemCatId=560
	 */
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		
		//1.先根据id查询对象
		ItemCat itemCat = 
				itemCatService.findItemCatById(itemCatId);
		//2.将对象中的name名称获取
		return itemCat.getName();
	}
	
	
	/**
	  *      获取商品分类列表信息
	 *  url:/item/cat/list
	  *      返回值: EasyUITree
	  *  
	  *  @RequestParam 获取参数实现数据的转化
	  *      参数说明: 
	  *      1.value/name   接收参数名称
	  *      2.defaultValue 默认值
	  *      3.required     是否必须传递,默认值true
	 */	
	@RequestMapping("/list")
	public List<EasyUITree> findItemCatByParentId
	(@RequestParam(value = "id",defaultValue = "0") Long parentId){
		
		return itemCatService.findItemCatByParentId(parentId);
		
		//先查询缓存,如果缓存中没有数据,则查询数据库
		//return itemCatService.findItemCatCache(parentId);
	}
	
}
