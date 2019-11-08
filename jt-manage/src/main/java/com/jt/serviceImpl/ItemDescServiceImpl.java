package com.jt.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemDescMapper;
import com.jt.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Override
	public void deleteItemDescByIds(Long[] ids) {
         List<Long> asList = Arrays.asList(ids);
		itemDescMapper.deleteBatchIds(asList);
	}

}
