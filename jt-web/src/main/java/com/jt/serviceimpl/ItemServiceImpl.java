package com.jt.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.anno.CacheFind;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClient;
	
	//jt-web服务器
	@Override
	@CacheFind
	public Item findItemById(Long itemId) {
		//连接jt-manage中服务
		String url = "http://manage.jt.com/web/item/findItemById";
		Map<String,String> params = new HashMap<String, String>();
		params.put("itemId", itemId+"");
		String itemJson = httpClient.doGet(url,params);
		
		return ObjectMapperUtil.toObject(itemJson, Item.class);
	}

	@Override
	@CacheFind
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById";
		Map<String,String> params = new HashMap<String, String>();
		params.put("itemId", itemId+"");
		String itemJson = httpClient.doGet(url,params);
		return ObjectMapperUtil.toObject(itemJson, ItemDesc.class);
	}
	
	
}
