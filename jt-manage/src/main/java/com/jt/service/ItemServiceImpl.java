package com.jt.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

	/*
	 * 1.查询商品总记录数
	 * 2.进行分页查询
	 *   
	 *   分页sql:  每页20条
	 *   第1页                                                    起始位置,展现条数
	 * 	 select * from tb_item limit 0,20 	 [0,19]
	 *   第2页
	 *   select * from tb_item limit 20,20 	 [20,39]
	 *   第3页
	 *   select * from tb_item limit 40,20 	 [40,59]
	 *    第N页
	 *   select * from tb_item order by updated limit (page-1)*rows,rows
	 *   
	 * mybatis-plus分页说明
	 * 1.new Page<>(current, size);
	 * 	 current:当前页数
	 * 	 size:   每页条数
	 */
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		
		//int total = itemMapper.selectCount(null);
		//int start = (page - 1) * rows;
		//List<Item> userList = 
				//itemMapper.findItemByPage(start,rows);
		
		Page<Item> tempPage = new Page<>(page, rows);
		QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
		queryWrapper.orderByDesc("updated");
		//当前查询的分页结果对象
		IPage<Item> IPage = 
				itemMapper.selectPage(tempPage, queryWrapper);
		//获取总记录数
		int total = (int) IPage.getTotal();
		//获取分页的结果
		List<Item> userList = IPage.getRecords();
		return new EasyUITable(total, userList);
	}

	
	/**
	 * 实现2张表同时入库
	 * @throws IOException 
	 * 
	 */
	@Override
	@Transactional	//事务控制
	public void saveItem(Item item,ItemDesc itemDesc){
		
		item.setStatus(1)	//表示正常状态
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//利用Mybatis-plus入库之后,会自动的将主键ID进行回显
		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}


	@Override
	@Transactional	//控制事物
	public void updateItem(Item item,ItemDesc itemDesc) {
		
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//所有数据都更新.
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


	@Transactional
	@Override
	public void deleteItem(Long[] ids) {
		List idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}


	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}


	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectById(itemId);
	}
	
	
	
	
	
	
	
	
	
	
}