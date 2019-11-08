package com.jt.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;

	public ItemCat findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat;
	}

	@Override
	@CacheFind
	public List<EasyUITree> findItemCatByParentId(Long parentId) {

		//1.查询数据
		List<ItemCat> itemCatList = 
					  findItemCatListByParentId(parentId);
		//2.实现数据封装
		List<EasyUITree> treeList = 
				new ArrayList<EasyUITree>(itemCatList.size());
		
		for (ItemCat itemCat : itemCatList) {
			Long id = itemCat.getId();
			String text = itemCat.getName();
			//如果是父级 closed,否则 表示3级标题 open
			String state = itemCat.getIsParent()?"closed":"open";
			EasyUITree tree = new EasyUITree(id, text, state);
			treeList.add(tree);
		}

		return treeList;
	}

	private List<ItemCat> findItemCatListByParentId(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = 
				itemCatMapper.selectList(queryWrapper);
		return itemCatList;
	}
	

	
    


}
