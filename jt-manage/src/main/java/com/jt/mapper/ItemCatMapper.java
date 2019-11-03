package com.jt.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;

public interface ItemCatMapper extends BaseMapper<ItemCat> {

	List<ItemCat> findItemCatByParentId(Long parentId);



}
