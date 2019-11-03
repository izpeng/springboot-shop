package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//		EasyUITable easyUITable = new EasyUITable();
		//		
		//		Page<Item> Ipage= new Page<>(page,rows);
		//		
		//		QueryWrapper qWrapper = new QueryWrapper();
		//		Object queryWrapper = qWrapper.orderByDesc("updated");
		//		itemMapper.selectPage(Ipage, queryWrapper);
		//		
		//		
		//		return easyUITable;
		//		
		EasyUITable easyUITable = new EasyUITable();
		int start = (page-1)*rows;
		List<Item> list = itemMapper.findItemByPage(start, rows);
		easyUITable.setRows(list);
		int count = itemMapper.selectCount(null);
		easyUITable.setTotal(count);
		return easyUITable;
	}

	@Override
	@Transactional
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1)
		.setCreated(new Date())
		.setUpdated(item.getCreated());
		itemMapper.insert(item);

		itemDesc.setItemId(item.getId())
		.setCreated(item.getCreated())
		.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);


	}

	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId())
		.setUpdated(item.getUpdated());
		
		itemDescMapper.updateById(itemDesc);
	}

	/**
	 * 任务:将ids中所有的数据的状态status改为2
	 */
	@Override
	public void updateStatus(Long[] ids, Integer status) {

		//1.小白级别
		/*
		 * for (Long id : ids) { 
		 * Item item = new Item(); 
		 * item.setId(id)
		 * .setStatus(status) .setUpdated(new Date()); 
		 * itemMapper.updateById(item); 
		 * }
		 */

		//2.菜鸟级别  sql
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		List idList = Arrays.asList(ids);
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}

	@Override
	public void deleteItem(Long[] ids) {
		List idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}






}
