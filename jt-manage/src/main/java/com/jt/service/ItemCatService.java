package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	ItemCat findItemCatById(Long itemCatId);

	List<EasyUITree> findItemCatByParentId(Long parentId);

	List<EasyUITree> findItemCatCache(Long parentId);

}
