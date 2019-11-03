package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	public List<Item> findItemByPage(@Param("start")Integer start, @Param("rows")Integer rows);
	
}
