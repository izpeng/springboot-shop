package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatService.findItemCatNameById(itemCatId);
		return itemCat.getName();

	}
	
	@RequestMapping("/list")
	public List<EasyUITree> findItemCatByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
     
	List<EasyUITree> findItemCatByParentId = itemCatService.findItemCatByParentId(parentId);
		
		return findItemCatByParentId;
	

	}
	
}
